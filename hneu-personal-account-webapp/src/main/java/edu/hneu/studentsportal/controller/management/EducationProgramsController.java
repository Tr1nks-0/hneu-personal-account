package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlerController;
import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.exceptions.CannotDeleteResourceException;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.FacultyService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.SpecialityService;
import javaslang.control.Try;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_EDUCATION_PROGRAMS_URL;
import static java.util.Objects.isNull;

@Log4j
@Controller
@RequestMapping(MANAGE_EDUCATION_PROGRAMS_URL)
public class EducationProgramsController implements ExceptionHandlerController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private FacultyService facultyService;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private SpecialityService specialityService;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String getEducationPrograms(@RequestParam(required = false) Long facultyId, @RequestParam(required = false) Long specialityId, Model model) {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyService.findByIdWithSpecialitiesOrDefault(facultyId);
        if (isNull(faculty))
            return "redirect:/management/specialities";
        Speciality speciality = specialityService.findByIdOrDefault(specialityId, faculty);
        return prepareEducationProgramPage(model, new EducationProgram(speciality));
    }

    @PostMapping
    public String createEducationProgram(@ModelAttribute @Valid EducationProgram educationProgram, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return prepareEducationProgramPage(model, educationProgram);
        } else {
            educationProgramRepository.save(educationProgram);
            log.info(String.format("New [%s] has been added", educationProgram.toString()));
            redirectAttributes.addFlashAttribute("success", "success.add.education.program");
            return "redirect:/management/education-programs?facultyId=" + educationProgram.getSpeciality().getFaculty().getId()
                    + "&specialityId=" + educationProgram.getSpeciality().getId();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        Try.run(() -> educationProgramRepository.delete(id)).onFailure(e -> {
            throw new CannotDeleteResourceException(e);
        });
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.educationProgramExistsError(), redirectAttributes);
    }

    @ExceptionHandler(CannotDeleteResourceException.class)
    public ResponseEntity<String> handleError(CannotDeleteResourceException e) {
        log.warn("Cannot delete education program due to: " + e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    public String baseUrl() {
        return MANAGE_EDUCATION_PROGRAMS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private String prepareEducationProgramPage(Model model, EducationProgram educationProgram) {
        Speciality speciality = educationProgram.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("educationProgram", educationProgram);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(faculty.getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("title", "management-education-programs");
        return "management/education-programs-page";
    }
}
package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlerController;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.exceptions.CannotDeleteResourceException;
import edu.hneu.studentsportal.repository.DisciplineRepository;
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
import java.util.Optional;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_DISCIPLINES_URL;
import static java.util.Objects.isNull;

@Log4j
@Controller
@RequestMapping(MANAGE_DISCIPLINES_URL)
public class DisciplinesController implements ExceptionHandlerController {

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
    private DisciplineRepository disciplineRepository;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String getDisciplines(@RequestParam(required = false) Long facultyId,
                                 @RequestParam(required = false) Long specialityId,
                                 @RequestParam(required = false) Long educationProgramId,
                                 @RequestParam(required = false, defaultValue = "1") int course,
                                 @RequestParam(required = false, defaultValue = "1") int semester, Model model) {
        if (facultyRepository.findAll().isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyService.findByIdWithSpecialitiesOrDefault(facultyId);
        if (isNull(faculty))
            return "redirect:/management/specialities";
        Speciality speciality = specialityService.findByIdOrDefault(specialityId, faculty);
        EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId);
        Discipline discipline = new Discipline();
        discipline.setEducationProgram(educationProgram);
        discipline.setSpeciality(speciality);
        discipline.setCourse(course);
        discipline.setSemester(semester);
        return prepareDisciplinesPage(model, discipline);
    }

    @PostMapping
    public String createDiscipline(@ModelAttribute @Valid Discipline discipline, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            return prepareDisciplinesPage(model, discipline);
        } else {
            disciplineRepository.save(discipline);
            log.info(String.format("New [%s] has been added", discipline.toString()));
            redirectAttributes.addFlashAttribute("success", "success.add.discipline");
            redirectAttributes.addAttribute("facultyId", discipline.getSpeciality().getFaculty().getId());
            redirectAttributes.addAttribute("specialityId", discipline.getSpeciality().getId());
            redirectAttributes.addAttribute("course", discipline.getCourse());
            redirectAttributes.addAttribute("semester", discipline.getSemester());
            Optional.ofNullable(discipline.getEducationProgram()).map(EducationProgram::getId).ifPresent(id ->
                    redirectAttributes.addAttribute("educationProgramId", id));
            return "redirect:/management/disciplines";
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        Try.run(() -> disciplineRepository.delete(id)).onFailure(e -> {
            throw new CannotDeleteResourceException(e);
        });
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.disciplineExistsError(), redirectAttributes);
    }

    @ExceptionHandler(CannotDeleteResourceException.class)
    public ResponseEntity<String> handleError(CannotDeleteResourceException e) {
        log.warn("Cannot delete discipline program due to: " + e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    public String baseUrl() {
        return MANAGE_DISCIPLINES_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private String prepareDisciplinesPage(Model model, Discipline discipline) {
        EducationProgram educationProgram = discipline.getEducationProgram();
        Speciality speciality = discipline.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("discipline", discipline);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(faculty.getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("controlForms", DisciplineFormControl.values());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplines", disciplineRepository.findByCourseAndSemesterAndSpecialityAndEducationProgram(
                discipline.getCourse(), discipline.getSemester(), speciality, educationProgram));
        model.addAttribute("title", "management-disciplines");
        return "management/disciplines-page";
    }
}
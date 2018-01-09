package edu.hneu.studentsportal.controller.management;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_DISCIPLINES_URL;
import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.*;
import static java.util.Objects.isNull;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Controller
@RequestMapping(MANAGE_DISCIPLINES_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DisciplinesManagementController extends AbstractManagementController {

    private final FacultyRepository facultyRepository;
    private final FacultyService facultyService;
    private final SpecialityRepository specialityRepository;
    private final SpecialityService specialityService;
    private final EducationProgramRepository educationProgramRepository;
    private final DisciplineRepository disciplineRepository;
    private final MessageService messageService;

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
        List<EducationProgram> educationPrograms = educationProgramRepository.findAllBySpeciality(speciality);
        if (educationPrograms.isEmpty())
            return "redirect:/management/education-programs?facultyId=" + faculty.getId() + "&specialityId=" + speciality.getId();
        EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId).orElse(educationPrograms.get(0));
        Discipline discipline = new Discipline();
        discipline.setEducationProgram(educationProgram);
        discipline.setSpeciality(speciality);
        discipline.setCourse(course);
        discipline.setSemester(semester);
        return prepareDisciplinesPage(model, discipline);
    }

    @PostMapping
    public String createDiscipline(@ModelAttribute @Valid Discipline discipline, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
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
        return handleErrorInternal(e, messageService.disciplinesExistError(), redirectAttributes);
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
        Speciality speciality = discipline.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("discipline", discipline);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(faculty.getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("controlForms", DisciplineFormControl.values());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplines", findAllDisciplinesLike(discipline));
        model.addAttribute("lastCourse", disciplineRepository.getLastCourse(speciality.getId(), discipline.getEducationProgram().getId()));
        model.addAttribute("title", "management-disciplines");
        return "management/disciplines-page";
    }

    private List<Discipline> findAllDisciplinesLike(Discipline discipline) {
        Specifications<Discipline> spec = where(hasCourseAndSemester(discipline.getCourse(), discipline.getSemester()))
                .and(hasSpeciality(discipline.getSpeciality()))
                .and(hasEducationProgram(discipline.getEducationProgram()));
        return disciplineRepository.findAll(spec);
    }
}
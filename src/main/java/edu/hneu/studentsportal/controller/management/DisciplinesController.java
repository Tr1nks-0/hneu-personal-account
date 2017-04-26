package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static java.util.Objects.isNull;

@Log4j
@Controller
@RequestMapping("/management/disciplines")
public class DisciplinesController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private DisciplineRepository disciplineRepository;

    @GetMapping
    public String getDisciplines(@RequestParam(required = false) Long facultyId,
                                 @RequestParam(required = false) Long specialityId,
                                 @RequestParam(required = false) Long educationProgramId,
                                 @RequestParam(required = false, defaultValue = "1") int course,
                                 @RequestParam(required = false, defaultValue = "1") int semester, Model model) {
        if (facultyRepository.findAll().isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyRepository.findByIdWithSpecialitiesOrDefault(facultyId);
        if (isNull(faculty))
            return "redirect:/management/specialities";
        Speciality speciality = specialityRepository.findByIdOrDefault(specialityId, faculty);
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
            redirectAttributes.addFlashAttribute("success", "success.add.discipline");
            redirectAttributes.addAttribute("facultyId", discipline.getSpeciality().getFaculty().getId());
            redirectAttributes.addAttribute("specialityId", discipline.getSpeciality().getId());
            redirectAttributes.addAttribute("course", discipline.getCourse());
            redirectAttributes.addAttribute("semester", discipline.getSemester());
            Optional.ofNullable(discipline.getEducationProgram()).map(EducationProgram::getId).ifPresent(id ->
                    redirectAttributes.addAttribute("educationProgramId", id)
            );
            return "redirect:/management/disciplines";
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        disciplineRepository.delete(id);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", "error.something.went.wrong");
        return "redirect:/management/disciplines";
    }

    private String prepareDisciplinesPage(Model model, Discipline discipline) {
        EducationProgram educationProgram = discipline.getEducationProgram();
        Speciality speciality = discipline.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("discipline", discipline);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFaculty(faculty));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("controlForms", DisciplineFormControl.values());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplines", disciplineRepository.findByCourseAndSemesterAndSpecialityAndEducationProgram(
                discipline.getCourse(), discipline.getSemester(), speciality, educationProgram));
        return "management/disciplines-page";
    }

}
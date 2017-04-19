package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Controller
@RequestMapping("/management/education-programs")
public class EducationProgramsController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;

    @GetMapping
    public String getEducationPrograms(@RequestParam(required = false) Long facultyId, @RequestParam(required = false) Long specialityId, Model model) {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty())
            return "redirect:/management/faculties";

        Faculty faculty = Optional.ofNullable(facultyId).map(facultyRepository::getOne).filter(containsSpecialities()).orElseGet(getFirstFacultyWithSpecialities(faculties));
        if (isNull(faculty))
            return "redirect:/management/specialities";

        List<Speciality> specialities = specialityRepository.findByFaculty(faculty);
        Speciality speciality = Optional.ofNullable(specialityId).map(specialityRepository::getOne).orElse(specialities.get(0));

        EducationProgram educationProgram = new EducationProgram();
        educationProgram.setSpeciality(speciality);

        model.addAttribute("educationProgram", educationProgram);
        model.addAttribute("faculties", faculties);
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialities);
        model.addAttribute("educationPrograms", educationProgramRepository.findBySpeciality(speciality));
        return "management/education-programs-page";
    }

    private Supplier<Faculty> getFirstFacultyWithSpecialities(List<Faculty> faculties) {
        return () -> faculties.stream().filter(containsSpecialities()).findFirst().orElse(null);
    }

    private Predicate<Faculty> containsSpecialities() {
        return faculty -> isFalse(specialityRepository.findByFaculty(faculty).isEmpty());
    }

    @PostMapping
    public String createEducationProgram(@ModelAttribute @Valid EducationProgram educationProgram, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Faculty faculty = educationProgram.getSpeciality().getFaculty();
        if(bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyRepository.findAll());
            model.addAttribute("selectedFaculty", faculty);
            model.addAttribute("specialities", specialityRepository.findByFaculty(faculty));
            model.addAttribute("educationPrograms", educationProgramRepository.findBySpeciality(educationProgram.getSpeciality()));
            return "management/education-programs-page";
        } else {
            educationProgramRepository.save(educationProgram);
            redirectAttributes.addFlashAttribute("success", "success.add.education.program");
            return "redirect:/management/education-programs?facultyId=" + faculty.getId()
                    + "&specialityId=" + educationProgram.getSpeciality().getId();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        educationProgramRepository.delete(id);
    }

}
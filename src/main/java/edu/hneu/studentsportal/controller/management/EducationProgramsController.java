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
        Optional<Faculty> faculty = facultyRepository.findFacultyByIdOrDefault(facultyId);
        if (isFalse(faculty.isPresent()))
            return "redirect:/management/specialities";
        List<Speciality> specialities = specialityRepository.findByFaculty(faculty.get());
        Speciality speciality = Optional.ofNullable(specialityId).map(specialityRepository::getOne).orElse(specialities.get(0));
        return prepareEducationProgramPage(model, new EducationProgram(speciality));
    }

    @PostMapping
    public String createEducationProgram(@ModelAttribute @Valid EducationProgram educationProgram, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return prepareEducationProgramPage(model, educationProgram);
        } else {
            educationProgramRepository.save(educationProgram);
            redirectAttributes.addFlashAttribute("success", "success.add.education.program");
            return "redirect:/management/education-programs?facultyId=" + educationProgram.getSpeciality().getFaculty().getId()
                    + "&specialityId=" + educationProgram.getSpeciality().getId();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        educationProgramRepository.delete(id);
    }

    private String prepareEducationProgramPage(Model model, EducationProgram educationProgram) {
        Speciality speciality = educationProgram.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("educationProgram", educationProgram);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findByFaculty(faculty));
        model.addAttribute("educationPrograms", educationProgramRepository.findBySpeciality(speciality));
        return "management/education-programs-page";
    }

}
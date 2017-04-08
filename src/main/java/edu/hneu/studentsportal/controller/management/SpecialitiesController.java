package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Log4j
@Controller
@RequestMapping("/management/specialities")
public class SpecialitiesController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;

    @GetMapping
    public String getSpecialities(@RequestParam(required = false) Long facultyId, Model model) {
        List<Faculty> allFaculties = facultyRepository.findAll();
        if(allFaculties.isEmpty())
            return "redirect:/management/faculties";

        allFaculties.stream().findFirst().ifPresent(defaultFaculty -> {
            Faculty faculty = Optional.ofNullable(facultyId).map(facultyRepository::getOne).orElse(defaultFaculty);

            Speciality specialityExample = new Speciality();
            specialityExample.setFaculty(faculty);

            model.addAttribute("faculties", allFaculties);
            model.addAttribute("newSpeciality", specialityExample);
            model.addAttribute("specialities", specialityRepository.findByFaculty(faculty));
        });
        return "management/specialities-page";
    }

    @PostMapping("/create")
    public String createFaculty(@ModelAttribute @Valid Speciality speciality, RedirectAttributes redirectAttributes) {
        specialityRepository.save(speciality);
        return "redirect:/management/specialities?facultyId=" + speciality.getFaculty().getId();
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        specialityRepository.delete(id);
    }

    @GetMapping(path = "/rest", params = "facultyId")
    @ResponseBody
    public List<Speciality> getSpecialitiesForFaculty(@RequestParam long facultyId) {
        Faculty faculty = facultyRepository.getOne(facultyId);
        return specialityRepository.findByFaculty(faculty);
    }
}
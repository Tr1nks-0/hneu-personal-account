package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
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
        List<Faculty> faculties = facultyRepository.findAll();
        if(faculties.isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = Optional.ofNullable(facultyId).map(facultyRepository::getOne).orElse(faculties.get(0));
        return prepareSpecialityPage(model, new Speciality(faculty));
    }

    @PostMapping
    public String createSpeciality(@ModelAttribute @Valid Speciality speciality, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return prepareSpecialityPage(model, speciality);
        } else {
            specialityRepository.save(speciality);
            redirectAttributes.addFlashAttribute("success", "success.add.speciality");
            return "redirect:/management/specialities?facultyId=" + speciality.getFaculty().getId();
        }
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

    private String prepareSpecialityPage(Model model, Speciality speciality) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("speciality", speciality);
        model.addAttribute("specialities", specialityRepository.findByFaculty(speciality.getFaculty()));
        return "management/specialities-page";
    }
}
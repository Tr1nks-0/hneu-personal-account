package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Log4j
@Controller
@RequestMapping("/management/faculties")
public class FacultiesController {

    @Resource
    private FacultyRepository facultyRepository;

    @GetMapping
    public String getFaculties(Model model) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("newFaculty", new Faculty());
        return "management/faculties-page";
    }

    @PostMapping("/create")
    public String createFaculty(@Valid @ModelAttribute Faculty faculty) {
        facultyRepository.save(faculty);
        return "redirect:/management/faculties";
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        facultyRepository.delete(id);
    }
}
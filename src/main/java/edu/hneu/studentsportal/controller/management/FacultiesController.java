package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.service.FacultyService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Log4j
@Controller
@RequestMapping("/management/faculties")
public class FacultiesController {

    @Resource
    private FacultyService facultyService;

    @GetMapping
    public String getFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("newFaculty", new Faculty());
        return "management/faculties-page";
    }

    @PostMapping("/create")
    public String createFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/management/faculties";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        facultyService.delete(id);
        return "redirect:/management/faculties";
    }
}
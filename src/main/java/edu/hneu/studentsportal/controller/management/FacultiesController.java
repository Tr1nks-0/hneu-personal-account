package edu.hneu.studentsportal.controller.management;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.service.FacultyService;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/management/faculties")
public class FacultiesController {

    @Resource
    private FacultyService facultyService;

    @GetMapping
    public String getFaculties(final Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("newFaculty", new Faculty());
        return "management/faculties-page";
    }

    @PostMapping("/create")
    public String createFaculty(@ModelAttribute final Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/management/faculties";
    }


    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable final long id) {
        facultyService.delete(id);
    }
}
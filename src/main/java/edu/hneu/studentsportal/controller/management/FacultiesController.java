package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return prepareFacultyPage(model, new Faculty());
    }

    @PostMapping
    public String createFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return prepareFacultyPage(model, faculty);
        } else {
            facultyRepository.save(faculty);
            redirectAttributes.addFlashAttribute("success", "success.add.faculty");
            return "redirect:/management/faculties";
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            facultyRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", "error.something.went.wrong");
        return "redirect:/management/faculties";
    }

    private String prepareFacultyPage(Model model, Faculty faculty) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("faculty", faculty);
        return "management/faculties-page";
    }
}
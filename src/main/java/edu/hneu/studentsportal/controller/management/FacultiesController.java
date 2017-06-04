package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.exceptions.CannotDeleteResourceException;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.service.MessageService;
import javaslang.control.Try;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_FACULTIES_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_FACULTIES_URL)
public class FacultiesController implements ExceptionHandlingController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private MessageService messageService;

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
    @ResponseBody
    public void delete(@PathVariable long id) {
        Try.run(() -> facultyRepository.delete(id)).onFailure(e -> {
            throw new CannotDeleteResourceException(e);
        });
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.facultyExistsError(), redirectAttributes);
    }

    @ExceptionHandler(CannotDeleteResourceException.class)
    public ResponseEntity<String> handleError(CannotDeleteResourceException e) {
        log.warn("Cannot delete faculty due to: " + e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    public String baseUrl() {
        return MANAGE_FACULTIES_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private String prepareFacultyPage(Model model, Faculty faculty) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("faculty", faculty);
        return "management/faculties-page";
    }
}
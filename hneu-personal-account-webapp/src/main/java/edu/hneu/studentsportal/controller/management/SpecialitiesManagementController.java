package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.exceptions.CannotDeleteResourceException;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.MessageService;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_SPECIALITIES_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_SPECIALITIES_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpecialitiesManagementController extends AbstractManagementController {

    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;
    private final MessageService messageService;

    @GetMapping
    public String getSpecialities(@RequestParam(required = false) Long facultyId, Model model) {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyRepository.findById(facultyId).orElseGet(() -> faculties.get(0));
        return prepareSpecialityPage(model, new Speciality(faculty));
    }

    @PostMapping
    public String createSpeciality(@ModelAttribute @Valid Speciality speciality, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return prepareSpecialityPage(model, speciality);
        } else {
            specialityRepository.save(speciality);
            log.info(String.format("New [%s] has been added", speciality.toString()));
            redirectAttributes.addFlashAttribute("success", "success.add.speciality");
            return "redirect:/management/specialities?facultyId=" + speciality.getFaculty().getId();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        Try.run(() -> specialityRepository.delete(id)).onFailure(e -> {
            throw new CannotDeleteResourceException(e);
        });
    }

    @GetMapping(path = "/rest", params = "facultyId")
    @ResponseBody
    public List<Speciality> getSpecialitiesForFaculty(@RequestParam long facultyId) {
        Faculty faculty = facultyRepository.getOne(facultyId);
        return specialityRepository.findAllByFacultyId(faculty.getId());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.specialityExistsError(), redirectAttributes);
    }

    @ExceptionHandler(CannotDeleteResourceException.class)
    public ResponseEntity<String> handleError(CannotDeleteResourceException e) {
        log.warn("Cannot delete speciality due to: " + e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    public String baseUrl() {
        return MANAGE_SPECIALITIES_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private String prepareSpecialityPage(Model model, Speciality speciality) {
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("speciality", speciality);
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(speciality.getFaculty().getId()));
        model.addAttribute("title", "management-specialities");
        return "management/specialities-page";
    }
}
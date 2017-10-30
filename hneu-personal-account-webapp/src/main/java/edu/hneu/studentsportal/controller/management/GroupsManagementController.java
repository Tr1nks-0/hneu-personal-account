package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.exceptions.CannotDeleteResourceException;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.FacultyService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.SpecialityService;
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
import java.util.Optional;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static java.util.Objects.isNull;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupsManagementController extends AbstractManagementController {

    private final FacultyRepository facultyRepository;
    private final FacultyService facultyService;
    private final SpecialityRepository specialityRepository;
    private final SpecialityService specialityService;
    private final EducationProgramRepository educationProgramRepository;
    private final GroupRepository groupRepository;
    private final MessageService messageService;

    @GetMapping
    public String getGroups(@RequestParam(required = false) Long facultyId,
                            @RequestParam(required = false) Long specialityId,
                            @RequestParam(required = false) Long educationProgramId,
                            Model model) {
        if (facultyRepository.findAll().isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyService.findByIdWithSpecialitiesOrDefault(facultyId);
        if (isNull(faculty))
            return "redirect:/management/specialities";
        Speciality speciality = specialityService.findByIdOrDefault(specialityId, faculty);
        EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId);
        Group group = new Group();
        group.setSpeciality(speciality);
        group.setEducationProgram(educationProgram);
        return prepareGroupPage(model, group);
    }

    @PostMapping
    public String createGroup(@ModelAttribute @Valid Group group, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareGroupPage(model, group);
        } else {
            groupRepository.save(group);
            log.info(String.format("New [%s] has been added", group.toString()));
            redirectAttributes.addFlashAttribute("success", "success.add.discipline");
            redirectAttributes.addAttribute("facultyId", group.getSpeciality().getFaculty().getId());
            redirectAttributes.addAttribute("specialityId", group.getSpeciality().getId());
            Optional.ofNullable(group.getEducationProgram()).map(EducationProgram::getId).ifPresent(id ->
                    redirectAttributes.addAttribute("educationProgramId", id));
            return "redirect:/management/groups";
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        Try.run(() -> groupRepository.delete(id)).onFailure(e -> {
            throw new CannotDeleteResourceException(e);
        });
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.groupExistsError(), redirectAttributes);
    }

    @ExceptionHandler(CannotDeleteResourceException.class)
    public ResponseEntity<String> handleError(CannotDeleteResourceException e) {
        log.warn("Cannot delete group due to: " + e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private String prepareGroupPage(Model model, Group group) {
        Speciality speciality = group.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("group", group);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(faculty.getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(speciality, group.getEducationProgram()));
        model.addAttribute("title", "management-groups");
        return "management/groups-page";
    }

}
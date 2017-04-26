package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Controller
@RequestMapping("/management/groups")
public class GroupsController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private GroupRepository groupRepository;

    @GetMapping
    public String getGroups(@RequestParam(required = false) Long facultyId,
                            @RequestParam(required = false) Long specialityId,
                            @RequestParam(required = false) Long educationProgramId,
                            Model model) {
        if (facultyRepository.findAll().isEmpty())
            return "redirect:/management/faculties";
        Faculty faculty = facultyRepository.findByIdWithSpecialitiesOrDefault(facultyId);
        if (isNull(faculty))
            return "redirect:/management/specialities";
        Speciality speciality = specialityRepository.findByIdOrDefault(specialityId, faculty);
        EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId);
        Group group = new Group();
        group.setSpeciality(speciality);
        group.setEducationProgram(educationProgram);
        return prepareGroupPage(model, group);
    }

    @PostMapping
    public String createGroup(@ModelAttribute @Valid Group group, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            return prepareGroupPage(model, group);
        } else {
            groupRepository.save(group);
            redirectAttributes.addFlashAttribute("success", "success.add.discipline");
            redirectAttributes.addAttribute("facultyId", group.getSpeciality().getFaculty().getId());
            redirectAttributes.addAttribute("specialityId", group.getSpeciality().getId());
            Optional.ofNullable(group.getEducationProgram()).map(EducationProgram::getId).ifPresent(id ->
                    redirectAttributes.addAttribute("educationProgramId", id)
            );
            return "redirect:/management/groups";
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        groupRepository.delete(id);
    }


    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", "error.something.went.wrong");
        return "redirect:/management/groups";
    }

    private String prepareGroupPage(Model model, Group group) {
        Speciality speciality = group.getSpeciality();
        Faculty faculty = speciality.getFaculty();
        model.addAttribute("group", group);
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialityRepository.findAllByFaculty(faculty));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(speciality, group.getEducationProgram()));
        return "management/groups-page";
    }

}
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
import org.springframework.web.bind.annotation.*;

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
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty())
            return "redirect:/management/faculties";

        Faculty faculty = Optional.ofNullable(facultyId).map(facultyRepository::getOne).filter(containsSpecialities()).orElseGet(getFirstFacultyWithSpecialities(faculties));
        if (isNull(faculty))
            return "redirect:/management/specialities";

        List<Speciality> specialities = specialityRepository.findByFaculty(faculty);
        Speciality speciality = Optional.ofNullable(specialityId).map(specialityRepository::getOne).orElse(specialities.get(0));

        EducationProgram educationProgram = Optional.ofNullable(educationProgramId).map(educationProgramRepository::getOne).orElse(null);

        Group group = new Group();
        group.setSpeciality(speciality);
        group.setEducationProgram(educationProgram);

        model.addAttribute("newGroup", group);
        model.addAttribute("faculties", faculties);
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialities);
        model.addAttribute("educationPrograms", educationProgramRepository.findBySpeciality(speciality));
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(speciality, educationProgram));
        return "management/groups-page";
    }

    private Supplier<Faculty> getFirstFacultyWithSpecialities(List<Faculty> faculties) {
        return () -> faculties.stream().filter(containsSpecialities()).findFirst().orElse(null);
    }

    private Predicate<Faculty> containsSpecialities() {
        return faculty -> isFalse(specialityRepository.findByFaculty(faculty).isEmpty());
    }

    @PostMapping("/create")
    public String createGroup(@ModelAttribute @Valid Group group) {
        groupRepository.save(group);

        String educationProgramParameter = Optional.ofNullable(group.getEducationProgram()).map(EducationProgram::getId)
                .map(id -> "&educationProgramId=" + id).orElse(StringUtils.EMPTY);
        long facultyId = group.getSpeciality().getFaculty().getId();
        long specialityId = group.getSpeciality().getId();
        return "redirect:/management/groups?faculty-id" + facultyId + "&speciality-id=" + specialityId + educationProgramParameter;
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        groupRepository.delete(id);
    }

}
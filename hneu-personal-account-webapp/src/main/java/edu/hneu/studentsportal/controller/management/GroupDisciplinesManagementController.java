package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupDisciplinesManagementController extends AbstractManagementController {

    private final DisciplineRepository disciplineRepository;
    private final GroupRepository groupRepository;

    @GetMapping("/{groupId}/disciplines")
    public String getDisciplines(@PathVariable Long groupId,
                                 @RequestParam(defaultValue = "1") int course,
                                 @RequestParam(defaultValue = "1") int semester, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("group", group);
        model.addAttribute("disciplines", getGroupDisciplines(group, course, semester));
        model.addAttribute("lastCourse", disciplineRepository.getLastCourse(group.getSpeciality().getId(), group.getEducationProgram().getId()));
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedSemester", semester);
        model.addAttribute("title", "management-disciplines");
        return "management/group-disciplines-page";
    }

    private List<Discipline> getGroupDisciplines(Group group, int course, int semester) {
        return disciplineRepository.findAll(where(hasSpeciality(group.getSpeciality()))
                .and(hasEducationProgram(group.getEducationProgram()))
                .and(hasCourseAndSemester(course, semester))
                .and(isNotTemporal()));
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
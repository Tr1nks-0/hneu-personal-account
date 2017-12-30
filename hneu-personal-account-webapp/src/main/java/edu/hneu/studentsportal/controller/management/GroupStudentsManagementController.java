package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupStudentsManagementController extends AbstractManagementController {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/{groupId}/students")
    public String getDisciplines(@PathVariable Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        List<Student> students = studentRepository.findByGroup(group, new Sort(Sort.Direction.ASC, "surname", "name"));
        model.addAttribute("students", students);
        model.addAttribute("group", group);
        model.addAttribute("title", "management-students");
        return "management/group-students-page";
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
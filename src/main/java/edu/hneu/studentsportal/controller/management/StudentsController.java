package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.EducationProgramService;
import edu.hneu.studentsportal.service.GroupService;
import edu.hneu.studentsportal.service.SpecialityService;
import edu.hneu.studentsportal.service.StudentService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Controller
@RequestMapping("/management/students")
public class StudentsController {

    @Resource
    private StudentService studentService;
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityService specialityService;
    @Resource
    private EducationProgramService educationProgramService;
    @Resource
    private GroupService groupService;
    @Resource
    private GroupRepository groupRepository;

    @GetMapping("/{id}")
    public String getStudent(@PathVariable long id, Model model) {
        Student student = studentService.getStudent(id);
        return prepareStudentEditorPage(model, student);
    }

    @GetMapping
    public String getStudents(@RequestParam Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        List<Student> students = studentRepository.findByGroup(group);
        model.addAttribute("students", students);
        model.addAttribute("group", group);
        return "management/group-students-page";
    }

    @PostMapping("/{id}")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult ,RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            return prepareStudentEditorPage(model, student);
        } else {
            studentService.save(student);
            redirectAttributes.addFlashAttribute("success", "success.save.student");
            return "redirect:/management/students/" + student.getId();
        }
    }

    @GetMapping("/{id}/remove")
    public String removeStudent(@PathVariable long id) {
        studentService.remove(id);
        return "redirect:/management/import/student";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", "error.something.went.wrong");
        return "redirect:/management/import/student";
    }

    private String prepareStudentEditorPage(Model model, Student student) {
        model.addAttribute("specialities", specialityService.getSpecialitiesForFaculty(student.getFaculty()));
        model.addAttribute("educationPrograms", educationProgramService.getEducationProgramsForSpeciality(student.getSpeciality()));
        model.addAttribute("groups", groupService.getGroups(student.getSpeciality(), student.getEducationProgram()));
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        model.addAttribute("student", student);
        return "management/student-editor-page";
    }
}

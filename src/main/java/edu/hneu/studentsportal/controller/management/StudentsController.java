package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.*;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Log4j
@Controller
@RequestMapping("/management/students")
public class StudentsController {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private GroupRepository groupRepository;

    @GetMapping("/{id}")
    public String getStudent(@PathVariable long id, Model model) {
        Student student = studentRepository.findOne(id);
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
            studentRepository.save(student);
            redirectAttributes.addFlashAttribute("success", "success.save.student");
            return "redirect:/management/students/" + student.getId();
        }
    }

    @GetMapping("/{id}/remove")
    public String removeStudent(@PathVariable long id) {
        studentRepository.delete(id);
        return "redirect:/management/import/student";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", "error.something.went.wrong");
        return "redirect:/management/import/student";
    }

    private String prepareStudentEditorPage(Model model, Student student) {
        model.addAttribute("specialities", specialityRepository.findAllByFaculty(student.getFaculty()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(student.getSpeciality()));
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(student.getSpeciality(), student.getEducationProgram()));
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        model.addAttribute("student", student);
        return "management/student-editor-page";
    }
}

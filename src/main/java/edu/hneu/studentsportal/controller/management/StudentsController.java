package edu.hneu.studentsportal.controller.management;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.service.StudentService;

@Controller
@RequestMapping("/management/students")
public class StudentsController {

    @Resource
    private StudentService studentService;

    @GetMapping("/{id}")
    public ModelAndView getStudent(@PathVariable long id, Model model) {
        Student student = studentService.getStudent(id);
        model.addAttribute("disciplineTypes", DisciplineType.values());
        return new ModelAndView("management/successfullyUploaded", "student", student);
    }

    @PostMapping("/{id}")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/management/import/student";
    }

}

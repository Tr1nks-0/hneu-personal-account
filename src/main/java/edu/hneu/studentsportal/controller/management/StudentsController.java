package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.service.StudentService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/management/students")
public class StudentsController {

    @Resource
    private StudentService studentService;

    @GetMapping("/{id}")
    public ModelAndView getStudent(@PathVariable long id, Model model) {
        Student student = studentService.getStudent(id);
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        return new ModelAndView("management/successfullyUploaded", "student", student);
    }

    @PostMapping("/{id}")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Success");
        return "redirect:/management/students/" + student.getId();
    }

    @GetMapping("/{id}/img")
    @SneakyThrows
    public void getPhoto(@PathVariable long id, HttpServletResponse response) {
        Student student = studentService.getStudent(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(student.getPhoto());
        outputStream.close();
    }

}

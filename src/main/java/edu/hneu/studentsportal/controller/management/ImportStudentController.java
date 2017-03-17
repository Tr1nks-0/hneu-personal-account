package edu.hneu.studentsportal.controller.management;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.StudentService;

@Controller
@RequestMapping("/management/import/student")
public class ImportStudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private FileService fileService;

    @GetMapping
    public String importStudent() {
        return "management/uploadUserProfilesFromExcel";
    }

    @PostMapping
    public String importStudent(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        File file = fileService.getFile(multipartFile);
        Student student = studentService.parseStudentProfile(file);
        studentService.sendEmailAfterProfileCreation(student);
        redirectAttributes.addFlashAttribute("student", student);
        return "redirect:/management/students/" + student.getId();
    }

}
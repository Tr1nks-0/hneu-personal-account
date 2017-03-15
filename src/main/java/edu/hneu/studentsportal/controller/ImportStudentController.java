package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;

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
        return "redirect:/management/import/student/review";
    }

    @GetMapping("/review")
    public String reviewResult() {
        return "management/successfullyUploaded";
    }

}
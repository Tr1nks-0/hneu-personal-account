package com.rozdolskyi.traininghneu.controller;

import com.rozdolskyi.traininghneu.model.FilesUploadModel;
import com.rozdolskyi.traininghneu.model.StudentProfile;
import com.rozdolskyi.traininghneu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String infoPage(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); //get logged in username
        File file = new File(servletContext.getRealPath("/WEB-INF/excel/Басова.xlsx"));
        StudentProfile studentProfile = studentService.readStudentProfilesFromFile(file);
        studentProfile.setEmail(email);
        studentService.save(studentProfile);
        return "info";
    }

    @RequestMapping
    public String account(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(email);
        if(studentProfile.isPresent()) {
            model.addAttribute("profile", studentProfile);
        }
        return "info";
    }

}
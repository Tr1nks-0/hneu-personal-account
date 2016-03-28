package com.rozdolskyi.traininghneu.controller;

import com.rozdolskyi.traininghneu.model.FilesUploadModel;
import com.rozdolskyi.traininghneu.model.StudentProfile;
import com.rozdolskyi.traininghneu.model.User;
import com.rozdolskyi.traininghneu.service.StudentService;
import com.rozdolskyi.traininghneu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserService userService;

    @RequestMapping
    public String infoPage(ModelMap model) {
        return "management";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadFiles(@ModelAttribute("uploadForm") FilesUploadModel uploadForm,
                                    HttpServletRequest request, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        String saveDirectory = servletContext.getRealPath("/WEB-INF/excel/uploaded");
        List<MultipartFile> uploadedFiles = uploadForm.getFiles();
        List<String> fileNames = new ArrayList<>();
        if (nonNull(uploadedFiles) && uploadedFiles.size() > 0) {
            for (MultipartFile multipartFile : uploadedFiles) {
                String fileName = multipartFile.getOriginalFilename();
                if (StringUtils.isNotEmpty(fileName)) {
                    File uploadedFile = new File(saveDirectory + fileName);
                    uploadedFile.delete();
                    multipartFile.transferTo(uploadedFile);
                    StudentProfile studentProfile = studentService.readStudentProfilesFromFile(uploadedFile);
                    setCredentials(studentProfile);
                    studentService.save(studentProfile);
                    fileNames.add(studentProfile.toString());
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:successfullyUploaded");
        redirectAttributes.addFlashAttribute("files",fileNames);
        return modelAndView;
    }

    private void setCredentials(StudentProfile studentProfile) {
        Optional<String> studentEmail = studentProfile.getContactInfo().stream().filter(contact -> contact.contains("@")).findFirst();
        if(studentEmail.isPresent()) {
            User user = new User();
            user.setId(studentEmail.get());
            studentProfile.setEmail(studentEmail.get());
            user.setPassword("0000");
            //studentProfile.setPassword(UUID.randomUUID().toString().substring(0, 8));
            user.setRole(2);
            userService.save(user);
        }
    }

    @RequestMapping(value = "/successfullyUploaded", method = RequestMethod.GET)
    public String successfullyUploaded() {
        return "successfullyUploaded";
    }

}
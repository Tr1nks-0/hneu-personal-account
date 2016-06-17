package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.model.FilesUploadModel;
import edu.hneu.studentsportal.model.Group;
import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.StudentService;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/management")
public class ManagementController {

    private static Logger LOG = Logger.getLogger(ManagementController.class.getName());

    @Autowired
    private StudentService studentService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private FileService fileService;
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/uploadStudentProfilesFromExcel")
    public String addUserProfilesFromExcel() {
        return "uploadUserProfilesFromExcel";
    }

    @RequestMapping(value = "/uploadStudentProfilesFromExcel", method = RequestMethod.POST)
    public String uploadFiles(@ModelAttribute("uploadForm") FilesUploadModel uploadForm,
                              RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        LOG.info("Files are going to upload");
        List<MultipartFile> filesToUpload = uploadForm.getFiles();
        String filePath = servletContext.getRealPath("/WEB-INF/excel/uploaded");
        Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload, filePath, uploadedFile -> {
            StudentProfile studentProfile = studentService.readStudentProfilesFromFile(uploadedFile);
            studentService.setGroupId(studentProfile);
            studentService.setCredentials(studentProfile);
            studentService.save(studentProfile);
            studentService.sendEmail(studentProfile);
        });
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        LOG.info("Files were uploaded");
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel", method = RequestMethod.POST)
    public String uploadTotalsFromExcel(@ModelAttribute("uploadForm") FilesUploadModel uploadForm,
                                        RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        List<MultipartFile> filesToUpload = uploadForm.getFiles();
        String filePath = servletContext.getRealPath("/WEB-INF/excel/uploaded");
        Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload, filePath,
                uploadedFile -> studentService.updateStudentsScoresFromFile(uploadedFile)
        );
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel")
    public String uploadTotalsFromExcel() {
        return "uploadTotalsFromExcel";
    }

    @RequestMapping(value = "/successfullyUploaded")
    public String successfullyUploaded() {
        return "successfullyUploaded";
    }

    @RequestMapping(value = "/synchronizeSchedule")
    public String synchronizeSchedulePage() {
        return "synchronizeSchedule";
    }

    @RequestMapping(value = "/downloadGroups")
    public String downloadGroups() {
        Executors.newSingleThreadExecutor().execute( () -> scheduleService.downloadGroups());
        return "redirect:scheduleSynchronizedSuccessfully";
    }

    @RequestMapping(value = "/scheduleSynchronizedSuccessfully")
    public String groupsDownloadedSuccessfully() {
        return "scheduleSynchronizedSuccessfully";
    }

}
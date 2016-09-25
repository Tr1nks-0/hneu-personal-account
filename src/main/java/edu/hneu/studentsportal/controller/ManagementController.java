package edu.hneu.studentsportal.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.hneu.studentsportal.model.FilesUploadModel;
import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.StudentService;

@Controller
@RequestMapping("/management")
public class ManagementController {

    private static final Logger LOG = Logger.getLogger(ManagementController.class.getName());

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
        return "management/uploadUserProfilesFromExcel";
    }

    @RequestMapping(value = "/uploadStudentProfilesFromExcel", method = RequestMethod.POST)
    public String uploadFiles(@ModelAttribute("uploadForm") final FilesUploadModel uploadForm,
                              final RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        LOG.info("Files are going to upload");
        final List<MultipartFile> filesToUpload = uploadForm.getFiles();
        final Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload, uploadedFile -> {
            final StudentProfile studentProfile = studentService.readStudentProfilesFromFile(uploadedFile);
            studentProfile.setFilePath("/individual-plan/" + uploadedFile.getName());
            studentService.setGroupId(studentProfile);
            studentService.setCredentials(studentProfile);
            studentService.save(studentProfile);
            studentService.sendEmailAfterProfileCreation(studentProfile);
        });
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        LOG.info("Files were uploaded");
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel", method = RequestMethod.POST)
    public String uploadTotalsFromExcel(@ModelAttribute("uploadForm") final FilesUploadModel uploadForm,
                                        final RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        final List<MultipartFile> filesToUpload = uploadForm.getFiles();
        final Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload,
                uploadedFile -> studentService.updateStudentsScoresFromFile(uploadedFile)
        );
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel")
    public String uploadTotalsFromExcel() {
        return "management/uploadTotalsFromExcel";
    }

    @RequestMapping(value = "/successfullyUploaded")
    public String successfullyUploaded() {
        return "management/successfullyUploaded";
    }

    @RequestMapping(value = "/synchronizeSchedule")
    public String synchronizeSchedulePage() {
        return "management/synchronizeSchedule";
    }

    @RequestMapping(value = "/students")
    public String studentsListPage(@RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "search-criteria", required = false) final String searchCriteria,
                                   final Model model) {
        if (isNull(page))
            page = 1;
        model.addAttribute("page", page);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("pagesCount", studentService.getPagesCountForSearchCriteria(searchCriteria));
        model.addAttribute("students", studentService.find(searchCriteria, page));
        return "management/studentsList";
    }

    @RequestMapping(value = "/students/{id:.+}")
    public ModelAndView editStudent(@PathVariable("id") final String id) {
        final StudentProfile studentProfile = studentService.findStudentProfileById(id);
        return new ModelAndView("management/studentEditor", "profile", studentProfile);
    }

    @RequestMapping(value = "/students/{id:.+}", method = RequestMethod.POST)
    public String editStudent(@PathVariable("id") final String id, @ModelAttribute("profile") final StudentProfile editedProfile) {
        final StudentProfile studentProfile = studentService.findStudentProfileById(id);
        if (nonNull(studentProfile)){
            studentProfile.setName(editedProfile.getName());
            studentProfile.setSurname(editedProfile.getSurname());
            studentProfile.setFaculty(editedProfile.getFaculty());
            studentProfile.setSpeciality(editedProfile.getSpeciality());
            studentProfile.setGroup(editedProfile.getGroup());
            studentProfile.setContactInfo(editedProfile.getContactInfo());
            studentService.save(studentProfile);
        }
        return "redirect:/management/students";
    }

    @RequestMapping(value = "/removeStudent")
    public String removeStudent(@RequestParam final String id) {
        studentService.remove(id);
        return "redirect:students";
    }

    @RequestMapping(value = "/downloadGroups")
    public String downloadGroups() {
        Executors.newSingleThreadExecutor().execute( () -> scheduleService.downloadGroups());
        return "redirect:scheduleSynchronizedSuccessfully";
    }

    @RequestMapping(value = "/scheduleSynchronizedSuccessfully")
    public String groupsDownloadedSuccessfully() {
        return "management/scheduleSynchronizedSuccessfully";
    }

}
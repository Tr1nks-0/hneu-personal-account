package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlerController;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import edu.hneu.studentsportal.service.MessageService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_STUDENTS_URL)
public class ImportStudentController implements ExceptionHandlerController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private EmailService emailService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String importStudent(Model model) {
        populateTitle(model);
        return "management/import-student-page";
    }

    @PostMapping
    @SneakyThrows
    public String importStudent(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
        File file = fileService.getFile(multipartFile);
        Student student = fileService.perform(file, importService::importStudent);
        emailService.sendProfileWasCreatedEmail(student);
        redirectAttributes.addFlashAttribute("student", student);
        populateTitle(model);
        return "redirect:/management/students/" + student.getId();
    }

    @Override
    public String baseUrl() {
        return IMPORT_STUDENTS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.studentExistsError(), redirectAttributes);
    }

    private void populateTitle(Model model) {
        model.addAttribute("title", "import-profile");
    }
}
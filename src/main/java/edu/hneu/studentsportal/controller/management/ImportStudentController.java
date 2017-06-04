package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_STUDENTS_URL)
public class ImportStudentController implements ExceptionHandlingController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private EmailService emailService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String importStudent() {
        return "management/import-student-page";
    }

    @PostMapping
    @SneakyThrows
    public String importStudent(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        File file = fileService.getFile(multipartFile);
        try {
            Student student = importService.importStudent(file);
            //emailService.sendProfileWasCreatedEmail(student);
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/management/students/" + student.getId();
        } finally {
            Files.deleteIfExists(file.toPath());
        }
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
}
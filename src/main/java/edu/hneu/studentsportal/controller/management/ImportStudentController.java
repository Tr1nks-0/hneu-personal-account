package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Optional;

@Log4j
@Controller
@RequestMapping("/management/import/student")
public class ImportStudentController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private MessageSource messageSource;
    @Resource
    private EmailService emailService;

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
            emailService.sendProfileWasCreatedEmail(student);
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/management/students/" + student.getId();
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleError(RuntimeException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage(), e);
        String error = Optional.ofNullable(e.getMessage()).orElse(messageSource.getMessage("error.something.went.wrong", new Object[0], Locale.getDefault()));
        redirectAttributes.addFlashAttribute("error", error);
        return "redirect:/management/import/student";
    }


}
package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.Set;

@Log4j
@Controller
@RequestMapping("/management/import/student-marks")
public class ImportStudentMarksController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private EmailService emailService;

    @GetMapping
    public String importStudent() {
        return "management/uploadTotalsFromExcel";
    }

    @PostMapping
    @SneakyThrows
    public String importStudent(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        File file = fileService.getFile(multipartFile);
        try {
            Set<Student> students = importService.importStudentMarks(file);
            students.forEach(emailService::sendProfileWasChangedEmail);
            redirectAttributes.addFlashAttribute("success", students);
        } finally {
            Files.deleteIfExists(file.toPath());
        }
        return "management/uploadTotalsFromExcel";
    }


    @ExceptionHandler(RuntimeException.class)
    public ModelAndView importStudentException(RuntimeException e) {
        log.warn(e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView("management/uploadTotalsFromExcel");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }

}
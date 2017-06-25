package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_MARKS_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_STUDENTS_MARKS_URL)
public class ImportStudentMarksController implements ExceptionHandlingController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private EmailService emailService;

    @GetMapping
    public String importStudent() {
        return "management/import-student-marks-page";
    }

    @PostMapping
    @SneakyThrows
    public String importStudent(@RequestParam("file") MultipartFile multipartFile, Model model) {
        File file = fileService.getFile(multipartFile);
        try {
            Map<Student, List<DisciplineMark>> studentsMarks = importService.importStudentMarks(file);
            studentsMarks.keySet().forEach(emailService::sendProfileWasChangedEmail);
            model.addAttribute("studentsMarks", studentsMarks);
        } finally {
            Files.deleteIfExists(file.toPath());
        }
        return "management/imported-students-marks";
    }

    @Override
    public String baseUrl() {
        return IMPORT_STUDENTS_MARKS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
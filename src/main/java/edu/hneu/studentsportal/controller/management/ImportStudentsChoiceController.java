package edu.hneu.studentsportal.controller.management;

import com.sun.org.apache.xpath.internal.operations.Mod;
import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.Discipline;
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

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_CHOICE_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_STUDENTS_CHOICE_URL)
public class ImportStudentsChoiceController implements ExceptionHandlingController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private EmailService emailService;

    @GetMapping
    public String importStudent(Model model) {
        populateTitle(model);
        return "management/import-students-choice-page";
    }

    @PostMapping
    @SneakyThrows
    public String importStudentsChoice(@RequestParam("file") MultipartFile multipartFile, Model model) {
        File file = fileService.getFile(multipartFile);
        Map<Student, List<Discipline>> studentsChoice = fileService.perform(file, importService::importStudentsChoice);
        studentsChoice.keySet().forEach(emailService::sendProfileWasChangedEmail);
        model.addAttribute("studentsChoice", studentsChoice);
        populateTitle(model);
        return "management/imported-students-choice";
    }

    @Override
    public String baseUrl() {
        return IMPORT_STUDENTS_CHOICE_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private void populateTitle(Model model) {
        model.addAttribute("title", "import-students-choice");
    }
}
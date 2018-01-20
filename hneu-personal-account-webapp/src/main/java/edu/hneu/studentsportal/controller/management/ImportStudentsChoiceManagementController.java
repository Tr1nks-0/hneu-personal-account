package edu.hneu.studentsportal.controller.management;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.validator.ExcelValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_CHOICE_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_STUDENTS_CHOICE_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportStudentsChoiceManagementController extends AbstractManagementController {

    private final ImportService importService;
    private final FileService fileService;
    private final EmailService emailService;

    @GetMapping
    public String importStudent(Model model) {
        populateTitle(model);
        return "management/import-students-choice-page";
    }

    @PostMapping
    @SneakyThrows
    public String importStudentsChoice(@RequestParam("file") MultipartFile multipartFile, Model model) {
        File file = fileService.getFile(multipartFile);
        ExcelValidator.validate(file);
        Map<Student, List<Discipline>> studentsChoice = fileService.perform(file, importService::importStudentsChoice);
        studentsChoice.keySet().forEach(emailService::sendProfileWasChangedEmail);
        model.addAttribute("studentsChoice", studentsChoice);
        populateTitle(model);
        return "management/imported-students-choice";
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class, InvocationTargetException.class})
    public String handleError(Exception e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.invalidFile(), redirectAttributes);
    }

    @ExceptionHandler({IOException.class, MultipartException.class})
    public String handleError(IOException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.fileNotFoundError(), redirectAttributes);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.studentExistsError(), redirectAttributes);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected String handleError(IllegalStateException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, e.getMessage(), redirectAttributes);
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
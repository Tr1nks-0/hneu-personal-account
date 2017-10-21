package edu.hneu.studentsportal.controller.management;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.hneu.studentsportal.controller.ExceptionHandlerController;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ImportService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.validator.ExcelValidator;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_DISCIPLINES_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_DISCIPLINES_URL)
public class ImportDisciplinesController implements ExceptionHandlerController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String importDisciplines(Model model) {
        populateTitle(model);
        return "management/import-disciplines-page";
    }

    @PostMapping
    public String importDisciplines(@RequestParam("file") MultipartFile multipartFile, Model model) {
        File file = fileService.getFile(multipartFile);
        ExcelValidator.validate(file);
        List<Discipline> disciplines = fileService.perform(file, importService::importDisciplines);
        model.addAttribute("disciplines", disciplines);
        populateTitle(model);
        return "management/imported-disciplines-page";
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class, InvocationTargetException.class})
    public String handleError(Exception e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.invalidFile(), redirectAttributes);
    }

    @ExceptionHandler(IOException.class)
    public String handleError(IOException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.fileNotFoundError(), redirectAttributes);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.studentExistsError(), redirectAttributes);
    }

    @Override
    public String baseUrl() {
        return IMPORT_DISCIPLINES_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private void populateTitle(Model model) {
        model.addAttribute("title", "import-disciplines");
    }
}
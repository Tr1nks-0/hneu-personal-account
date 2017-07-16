package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.Discipline;
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

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_DISCIPLINES_URL;

@Log4j
@Controller
@RequestMapping(IMPORT_DISCIPLINES_URL)
public class ImportDisciplinesController implements ExceptionHandlingController {

    @Resource
    private ImportService importService;
    @Resource
    private FileService fileService;

    @GetMapping
    public String importDisciplines() {
        return "management/import-disciplines-page";
    }

    @PostMapping
    public String importDisciplines(@RequestParam("file") MultipartFile multipartFile, Model model) {
        File file = fileService.getFile(multipartFile);
        List<Discipline> disciplines = fileService.perform(file, importService::importDisciplines);
        model.addAttribute("disciplines", disciplines);
        return "management/imported-disciplines-page";
    }

    @Override
    public String baseUrl() {
        return IMPORT_DISCIPLINES_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.feature.SiteFeature;
import edu.hneu.studentsportal.service.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static edu.hneu.studentsportal.controller.ControllerConstants.IMPORT_STUDENTS_URL;
import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_CONFIGURATIONS_URL;
import static java.util.Objects.nonNull;

@Log4j
@Controller
@RequestMapping(MANAGE_CONFIGURATIONS_URL)
public class ConfigurationsController implements ExceptionHandlingController {

    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String getConfigs(Model model) {
        model.addAttribute("admins", userService.getAdmins());
        return "management/configurations-page";
    }

    @PostMapping("/email-sending")
    public String saveEmailConfigs(HttpServletRequest request) {
        BiConsumer<String, SiteFeature> changeFeatureStateIfNeeded = (name, feature) -> {
            boolean flag = nonNull(request.getParameter(name));
            if(flag != feature.isActive())
                feature.toggle();
        };
        changeFeatureStateIfNeeded.accept("sendEmailAfterProfileCreation", SiteFeature.SEND_EMAIL_AFTER_PROFILE_CREATION);
        changeFeatureStateIfNeeded.accept("sendEmailAfterImportingMarks", SiteFeature.SEND_EMAIL_AFTER_PROFILE_MODIFICATION);
        return "redirect:" + MANAGE_CONFIGURATIONS_URL;
    }

    @PostMapping("/admins")
    public String addAdmin(HttpServletRequest request) {
        String email = request.getParameter("email");
        userService.create(email, UserRole.ADMIN);
        return "redirect:" + MANAGE_CONFIGURATIONS_URL;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.adminExistsError(), redirectAttributes);
    }

    @Override
    public String baseUrl() {
        return MANAGE_CONFIGURATIONS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
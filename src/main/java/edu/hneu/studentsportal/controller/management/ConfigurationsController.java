package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlerController;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.feature.SiteFeature;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.UserService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.function.BiConsumer;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_CONFIGURATIONS_URL;
import static java.util.Objects.nonNull;

@Log4j
@Controller
@RequestMapping(MANAGE_CONFIGURATIONS_URL)
public class ConfigurationsController implements ExceptionHandlerController {

    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String getConfigs(Model model) {
        model.addAttribute("admins", userService.getAdmins());
        model.addAttribute("title", "settings");
        model.addAttribute("SEND_EMAIL_AFTER_PROFILE_CREATION", SiteFeature.SEND_EMAIL_AFTER_PROFILE_CREATION.isActive());
        model.addAttribute("SEND_EMAIL_AFTER_PROFILE_MODIFICATION", SiteFeature.SEND_EMAIL_AFTER_PROFILE_MODIFICATION.isActive());
        return "management/configurations-page";
    }

    @PostMapping("/email-sending")
    public String saveEmailConfigs(HttpServletRequest request) {
        BiConsumer<String, SiteFeature> changeFeatureStateIfNeeded = (name, feature) -> {
            boolean flag = nonNull(request.getParameter(name));
            if (flag != feature.isActive())
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
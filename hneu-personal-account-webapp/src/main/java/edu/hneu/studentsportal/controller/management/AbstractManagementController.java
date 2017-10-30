package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

public abstract class AbstractManagementController {

    @Resource
    protected MessageService messageService;

    public abstract String baseUrl();

    public abstract Logger logger();

    @ExceptionHandler(Exception.class)
    protected String handleError(Exception e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.somethingWentWrong(), redirectAttributes);
    }

    protected String handleErrorInternal(Throwable cause, String errorMessage, RedirectAttributes redirectAttributes) {
        logger().warn(cause.getMessage(), cause);
        redirectAttributes.addFlashAttribute("error", errorMessage);
        return "redirect:" + baseUrl();
    }
}

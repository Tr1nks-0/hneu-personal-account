package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.service.CustomUserDetailsService;
import edu.hneu.studentsportal.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;

@Log4j
@Controller
@RequestMapping("/account/contactUs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactUsAccountController {

    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;
    private final OAuth2RestOperations oAuth2RestTemplate;

    @GetMapping
    public String contactUs(@RequestParam(required = false) Boolean success, Model model) {
        model.addAttribute("success", success);
        model.addAttribute("title", "top.menu.contacts");
        return "student/contact-us";
    }

    @PostMapping
    public String contactUs(@RequestParam String message, Principal principal) {
        String email = userDetailsService.extractUserEmail(principal);
        String accessToken = oAuth2RestTemplate.getAccessToken().getValue();
        emailService.sendContactUsEmail(email, accessToken, message);
        return "redirect:contactUs?success=true";
    }
}

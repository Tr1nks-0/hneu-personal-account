package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.service.StudentService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MailSender mailSender;
    @Value("${support.mail}")
    public String supportMail;

    @RequestMapping
    public ModelAndView account(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasAdminRole(auth.getAuthorities()))
            return new ModelAndView("redirect:management/uploadStudentProfilesFromExcel");
        Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(auth.getName());
        if (studentProfile.isPresent()) {
            session.setAttribute("groupId", studentProfile.get().getGroupId());
            return new ModelAndView("account", "profile", studentProfile.get());
        }
        return new ModelAndView("redirect:login");
    }

    @RequestMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    @RequestMapping("/documents")
    public String documents() {
        return "documents";
    }

    @RequestMapping("/contactUs")
    public String contactUs(@RequestParam(required = false) Boolean success, Model model) {
        model.addAttribute("success", success);
        return "contactUs";
    }

    @RequestMapping("/sendEmail")
    public String contactUs(@RequestParam String message, HttpSession session) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //// TODO: 27.06.16 remove comments
        //simpleMailMessage.setFrom(getGroupId(session));
        simpleMailMessage.setFrom(supportMail);
        simpleMailMessage.setTo(supportMail);
        simpleMailMessage.setSubject("Зворотній зв'язок | Кабінет студнта");
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
        return "redirect:contactUs?success=true";
    }

    @ModelAttribute(value = "groupId")
    public String getGroupId(HttpSession session) {
        return (String) session.getAttribute("groupId");
    }

    private boolean hasAdminRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
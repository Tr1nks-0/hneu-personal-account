package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

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
    public ModelAndView account(final HttpSession session, final Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasAdminRole(auth.getAuthorities()))
            return new ModelAndView("redirect:management/uploadStudentProfilesFromExcel");
        final Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(auth.getName());
        if (studentProfile.isPresent()) {
            model.addAttribute("title", "top.menu.home");
            session.setAttribute("groupId", studentProfile.get().getGroupId());
            return new ModelAndView("student/account", "profile", studentProfile.get());
        }
        return new ModelAndView("redirect:login");
    }

    @ModelAttribute(value = "profile")
    public StudentProfile getProfile() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(auth.getName());
        return studentProfile.orElse(null);
    }

    @RequestMapping("/schedule")
    public String schedule(final Model model) {
        String url = "http://services.ksue.edu.ua:8081/schedule/xml?auth=com.alcsan.atimetable_19092013_552ca3ffa5&group=21382&week=2";
        Schedule schedule = new RestTemplate().getForObject(url, Schedule.class);
        model.addAttribute("schedule", schedule);
        model.addAttribute("title", "top.menu.schedule");
        return "student/schedule";
    }

    @RequestMapping("/documents")
    public String documents(final Model model) {
        model.addAttribute("title", "top.menu.documents");
        return "student/documents";
    }

    @RequestMapping("/contactUs")
    public String contactUs(@RequestParam(required = false) final Boolean success, final Model model) {
        model.addAttribute("success", success);
        model.addAttribute("title", "top.menu.contacts");
        return "student/contactUs";
    }

    @RequestMapping("/sendEmail")
    public String contactUs(@RequestParam final String message, final HttpSession session) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
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
    public String getGroupId(final HttpSession session) {
        return (String) session.getAttribute("groupId");
    }

    private boolean hasAdminRole(final Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
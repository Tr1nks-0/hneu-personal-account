package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.entity.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private UserService userService;
    @Resource
    private CustomUserDetailsService userDetailsService;
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private EmailService emailService;
    @Autowired
    private OAuth2RestOperations oAuth2RestTemplate;

    @RequestMapping
    public ModelAndView account(HttpSession session, Model model, Principal principal) {
        String email = userDetailsService.extractUserEmail(principal);
        if (email != null) {
            Optional<User> currentUser = Optional.ofNullable(userService.getUserForId(email));
            if (currentUser.isPresent() && currentUser.get().getRole() == UserRole.ADMIN)
                return new ModelAndView("redirect:management/import/student");
            Student profile = studentRepository.findByEmail(email);
            if (profile != null) {
                Student studentProfile = profile;
                model.addAttribute("title", "top.menu.home");
                model.addAttribute("currentCourse", scheduleService.getCurrentCourse(studentProfile));
                session.setAttribute("groupId", studentProfile.getGroup().getId());
                session.setAttribute("email", studentProfile.getEmail());
                return new ModelAndView("student/account", "profile", studentProfile);
            }
        }
        SecurityContextHolder.clearContext();
        return new ModelAndView("redirect:login?error");
    }

    @ModelAttribute(value = "profile")
    public Student getProfile(HttpSession session, Principal principal) {
        String email = Optional.ofNullable((String) session.getAttribute("email")).orElseGet(() -> userDetailsService.extractUserEmail(principal));
        return studentRepository.findByEmail(email);
    }

    @RequestMapping("/schedule")
    public String schedule(@ModelAttribute("profile") Student profile, @RequestParam(required = false) Long week, Model model) {
        Schedule schedule = scheduleService.load(profile.getGroup().getId(), week);
        model.addAttribute("pairs", scheduleService.getPairs(schedule));
        model.addAttribute("days", schedule.getWeek().getDay());
        model.addAttribute("week", week);
        model.addAttribute("title", "top.menu.schedule");
        return "student/schedule";
    }

    @RequestMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("title", "top.menu.documents");
        return "student/documents";
    }

    @RequestMapping("/contactUs")
    public String contactUs(@RequestParam(required = false) Boolean success, Model model) {
        model.addAttribute("success", success);
        model.addAttribute("title", "top.menu.contacts");
        return "student/contactUs";
    }

    @RequestMapping("/sendEmail")
    public String contactUs(@RequestParam String message, Principal principal) {
        String email = userDetailsService.extractUserEmail(principal);
        String accessToken = oAuth2RestTemplate.getAccessToken().getValue();
        emailService.sendContactUsEmail(email, accessToken, message);
        return "redirect:contactUs?success=true";
    }

}

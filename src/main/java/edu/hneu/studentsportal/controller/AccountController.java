package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.entity.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.*;
import edu.hneu.studentsportal.service.impl.DefaultEmailService;
import edu.hneu.studentsportal.service.impl.GmailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
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
    private GmailService gmailService;
    @Resource
    private DefaultEmailService emailService;

    @Value("${decan.mail}")
    public String decanMail;
    @Value("${support.mail}")
    public String supportMail;

    @Resource
    private ScheduleService scheduleService;

    @RequestMapping
    public ModelAndView account(final HttpSession session, final Model model, final Principal principal) {
        final Optional<String> email = userDetailsService.extractUserEmail((Map<String, List<Object>>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails());
        if (email.isPresent()) {
            final Optional<User> currentUser = Optional.ofNullable(userService.getUserForId(email.get()));
            if (currentUser.isPresent() && currentUser.get().getRole() == UserRole.ADMIN)
                return new ModelAndView("redirect:management/import/student");
            final Optional<Student> profile = studentRepository.findByEmail(email.get());
            if (profile.isPresent()) {
                final Student studentProfile = profile.get();
                model.addAttribute("title", "top.menu.home");
                model.addAttribute("currentCourse", getCurrentCourse(studentProfile));
                session.setAttribute("groupId", studentProfile.getGroup().getId());
                session.setAttribute("email", studentProfile.getEmail());
                return new ModelAndView("student/account", "profile", studentProfile);
            }
        }
        SecurityContextHolder.clearContext();
        return new ModelAndView("redirect:login?error");
    }

    private int getCurrentCourse(final Student studentProfile) {
        return LocalDate.now().getYear() - studentProfile.getIncomeYear() + 1;
    }

    @ModelAttribute(value = "profile")
    public Student getProfile(final HttpSession session, final Principal principal) {
        String email = (String) session.getAttribute("email");
        if (isNull(email)) {
            final Map<String, List<Object>> details = (Map<String, List<Object>>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            email = userDetailsService.extractUserEmail(details).orElse(StringUtils.EMPTY);
        }
        return studentRepository.findByEmail(email).orElse(null);
    }

    @RequestMapping("/schedule")
    public String schedule(@ModelAttribute("profile") Student profile , @RequestParam(required = false) Long week, Model model) {
        Schedule schedule = scheduleService.load(profile.getGroup().getId(), week);
        model.addAttribute("pairs", scheduleService.getPairs(schedule));
        model.addAttribute("days", schedule.getWeek().getDay());
        model.addAttribute("week", week);
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
    public String contactUs(@RequestParam final String message, final HttpSession session, final Principal principal) {
        /*    //@formatter:off
        final String userEmail = getProfile(session, principal).getEmail();
        final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(
                userEmail, decanMail)
                .setSubject("Кабінет студента | Спілкування з деканом")
                .setText(message, false)
                .build();
        //@formatter:on
        gmailService.api().users().messages().send(userEmail, gmailService.convertToGmailMessage(mimeMessage)).execute();*/
        return "redirect:contactUs?success=true";
    }

}

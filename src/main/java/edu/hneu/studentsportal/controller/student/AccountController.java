package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.domain.dto.schedule.Schedule;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.ConnectException;
import java.security.Principal;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j
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
    @Resource
    private DisciplineMarkService disciplineMarkService;
    @Autowired
    private OAuth2RestOperations oAuth2RestTemplate;

    @GetMapping
    public ModelAndView account(HttpSession session, Model model, Principal principal, HttpServletRequest request) {
        String email = userDetailsService.extractUserEmail(principal);
        if (isNull(email))
            return redirectToLoginWithError(request);

        User currentUser = userService.getUserForId(email);
        if (nonNull(currentUser) && currentUser.getRole() == UserRole.ADMIN)
            return new ModelAndView("redirect:management/import/student");

        Student student = studentRepository.findByEmail(email);
        if (isNull(student)) {
            return redirectToLoginWithError(request);
        }

        model.addAttribute("title", "top.menu.home");
        model.addAttribute("currentCourse", scheduleService.getCurrentCourse(student));
        session.setAttribute("groupId", student.getGroup().getId());
        session.setAttribute("email", student.getEmail());
        model.addAttribute("courses", disciplineMarkService.getStudentCourses(student));
        return new ModelAndView("student/account", "profile", student);
    }

    @GetMapping("/schedule")
    public String schedule(@ModelAttribute("profile") Student profile, @RequestParam(required = false) Long week, Model model) {
        week = scheduleService.getWeekOrDefault(week);
        Schedule schedule = scheduleService.load(profile.getGroup().getId(), week);
        model.addAttribute("pairs", scheduleService.getPairs(schedule));
        model.addAttribute("days", schedule.getWeek().getDay());
        model.addAttribute("week", week);
        model.addAttribute("title", "top.menu.schedule");
        return "student/schedule";
    }

    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("title", "top.menu.documents");
        return "student/documents";
    }

    @GetMapping("/contactUs")
    public String contactUs(@RequestParam(required = false) Boolean success, Model model) {
        model.addAttribute("success", success);
        model.addAttribute("title", "top.menu.contacts");
        return "student/contactUs";
    }

    @PostMapping("/sendEmail")
    public String contactUs(@RequestParam String message, Principal principal) {
        String email = userDetailsService.extractUserEmail(principal);
        String accessToken = oAuth2RestTemplate.getAccessToken().getValue();
        emailService.sendContactUsEmail(email, accessToken, message);
        return "redirect:contactUs?success=true";
    }

    @ModelAttribute(value = "profile")
    public Student getProfile(HttpSession session, Principal principal) {
        String email = Optional.ofNullable((String) session.getAttribute("email")).orElseGet(() -> userDetailsService.extractUserEmail(principal));
        return studentRepository.findByEmail(email);
    }


    @ExceptionHandler(ConnectException.class)
    public ModelAndView handleError(HttpServletRequest request) throws ServletException {
        return redirectToLoginWithError(request);
    }

    @SneakyThrows
    private ModelAndView redirectToLoginWithError(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.logout();
        return new ModelAndView("redirect:login?error");
    }
}

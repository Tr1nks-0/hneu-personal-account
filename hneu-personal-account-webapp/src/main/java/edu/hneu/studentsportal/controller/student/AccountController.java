package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.ConnectException;
import java.security.Principal;

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
    private DisciplineMarkService disciplineMarkService;

    @GetMapping
    public ModelAndView account(HttpSession session, Model model, Principal principal, HttpServletRequest request) {
        String email = userDetailsService.extractUserEmail(principal);
        if (isNull(email))
            return redirectToLoginWithError(request);
        User currentUser = userService.getUserForId(email);
        if (nonNull(currentUser) && currentUser.getRole() == UserRole.ADMIN)
            return new ModelAndView("redirect:management/import/student");
        Student student = studentRepository.findByEmail(email);
        if (isNull(student))
            return redirectToLoginWithError(request);
        model.addAttribute("title", "top.menu.home");
        model.addAttribute("currentCourse", scheduleService.getCurrentCourse(student));
        session.setAttribute("groupId", student.getGroup().getId());
        session.setAttribute("email", student.getEmail());
        model.addAttribute("groupedMarks", disciplineMarkService.getStudentMarksGroupedByCourseAndSemester(student));
        return new ModelAndView("student/account", "profile", student);
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

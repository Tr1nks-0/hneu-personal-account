package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.ConnectException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final ScheduleService scheduleService;
    private final DisciplineMarkService disciplineMarkService;

    @GetMapping
    public ModelAndView account(@RequestParam(required = false) Integer course, HttpSession session,
                                Model model, Principal principal, HttpServletRequest request) {
        String email = userDetailsService.extractUserEmail(principal);
        if (isNull(email))
            return redirectToLoginWithError(request);
        User currentUser = userService.getUserForId(email);
        if (nonNull(currentUser) && currentUser.getRole() == UserRole.ADMIN)
            return new ModelAndView("redirect:management/import/student");
        Student student = studentRepository.findByEmail(email);
        if (isNull(student))
            return redirectToLoginWithError(request);

        List<Integer> courses = disciplineMarkService.getStudentCourses(student);
        int currentCourse = scheduleService.getCurrentCourse(student);
        int selectedCourse = nonNull(course) && courses.contains(course) ? course : currentCourse;
        Map<Integer, List<DisciplineMark>> marks = getMarks(student, selectedCourse);

        model.addAttribute("title", "top.menu.home");
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourse", selectedCourse);
        model.addAttribute("groupedMarks", marks);
        session.setAttribute("groupId", student.getGroup().getId());
        session.setAttribute("email", student.getEmail());
        return new ModelAndView("student/account", "profile", student);
    }

    private Map<Integer, List<DisciplineMark>> getMarks(Student student, int currentCourse) {
        return disciplineMarkService.getStudentMarksGroupedBySemester(disciplineMarkService.getStudentMarks(student, currentCourse));
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

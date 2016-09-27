package edu.hneu.studentsportal.controller;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.model.User;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.TimeService;
import edu.hneu.studentsportal.service.UserService;
import edu.hneu.studentsportal.service.impl.DefaultEmailService;
import edu.hneu.studentsportal.service.impl.GmailService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private UserService userService;
    @Autowired
    private GmailService gmailService;
    @Autowired
    private DefaultEmailService emailService;

    @Value("${support.mail}")
    public String supportMail;
    @Value("${schedule.url}")
    public String scheduleUrl;

    @RequestMapping
    public ModelAndView account(final HttpSession session, final Model model, final Principal principal) {
        final Optional<String> email = userService
                .extractUserEmailFromDetails((LinkedHashMap) ((OAuth2Authentication) principal).getUserAuthentication().getDetails());
        if (email.isPresent()) {
            final Optional<User> currentUser = userService.getUserForId(email.get());
            if (currentUser.isPresent() && currentUser.get().getRole() == 1)
                return new ModelAndView("redirect:management/uploadStudentProfilesFromExcel");
            final Optional<StudentProfile> profile = studentService.findStudentProfileByEmail(email.get());
            if (profile.isPresent()) {
                final StudentProfile studentProfile = profile.get();
                model.addAttribute("title", "top.menu.home");
                model.addAttribute("currentCourse", getCurrentCourse(studentProfile));
                session.setAttribute("groupId", studentProfile.getGroupId());
                session.setAttribute("email", studentProfile.getEmail());
                return new ModelAndView("student/account", "profile", studentProfile);
            }
        }
        SecurityContextHolder.clearContext();
        return new ModelAndView("redirect:login?error");
    }

    private int getCurrentCourse(final StudentProfile studentProfile) {
        return timeService.getCurrentDate().getYear() - studentProfile.getIncomeYear() + 1;
    }

    @ModelAttribute(value = "profile")
    public StudentProfile getProfile(final HttpSession session, final Principal principal) {
        String email = (String) session.getAttribute("email");
        if (isNull(email)) {
            final LinkedHashMap details = (LinkedHashMap) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            email = userService.extractUserEmailFromDetails(details).orElse(StringUtils.EMPTY);
        }
        return studentService.findStudentProfileByEmail(email).orElse(null);
    }

    @RequestMapping("/schedule")
    public String schedule(final Model model, @RequestParam(required = false) Integer week, final HttpSession session, final Principal principal) {
        try {
            if (isNull(week))
                week = timeService.getCurrentEducationWeek();
            final String groupCode = getProfile(session, principal).getGroupId();
            final String url = String.format(scheduleUrl, groupCode, week);

            final Schedule schedule = new RestTemplate().getForObject(url, Schedule.class);

            final Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> pairs = extractPairs(schedule);
            final List<Schedule.Week.Day> days = schedule.getWeek().getDay();

            model.addAttribute("pairs", pairs);
            model.addAttribute("days", days);
            model.addAttribute("week", week);
        } catch (final RuntimeException e) {
            //
        }
        model.addAttribute("title", "top.menu.schedule");
        return "student/schedule";
    }

    private Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> extractPairs(final Schedule schedule) {
        final Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> pairs = new HashedMap(7);
        for (byte i = 0; i < 7; i++) {
            pairs.put(Integer.valueOf(i), new HashedMap(8));
        }
        for (final Schedule.ScheduleElements.ScheduleElement scheduleElement : schedule.getScheduleElements().getScheduleElement()) {
            final Map<Integer, Schedule.ScheduleElements.ScheduleElement> days = pairs.get(Integer.valueOf(scheduleElement.getPair()));
            days.put(Integer.valueOf(scheduleElement.getDay()), scheduleElement);
            pairs.put(Integer.valueOf(scheduleElement.getPair()), days);
        }
        return pairs;
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
    public String contactUs(@RequestParam final String message, final HttpSession session, final Principal principal) throws MessagingException, IOException {
        //@formatter:off
        final String userEmail = getProfile(session, principal).getEmail();
        final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(
                userEmail, "dekanstei@gmail.com")
                .setSubject("Кабінет студента | Спілкування з деканом")
                .setText(message, false)
                .build();
        //@formatter:on
        gmailService.api().users().messages().send(userEmail, gmailService.convertToGmailMessage(mimeMessage)).execute();
        return "redirect:contactUs?success=true";
    }

    @ModelAttribute(value = "groupId")
    public String getGroupId(final HttpSession session) {
        return (String) session.getAttribute("groupId");
    }

}

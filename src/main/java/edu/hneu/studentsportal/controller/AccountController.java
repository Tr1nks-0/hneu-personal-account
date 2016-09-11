package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.TimeService;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private TimeService timeService;

    @Value("${support.mail}")
    public String supportMail;
    @Value("${schedule.url}")
    public String scheduleUrl;

    @RequestMapping
    public ModelAndView account(final HttpSession session, final Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasAdminRole(auth.getAuthorities()))
            return new ModelAndView("redirect:management/uploadStudentProfilesFromExcel");
        final Optional<StudentProfile> profile = studentService.findStudentProfileByEmail(auth.getName());
        if(profile.isPresent()) {
            StudentProfile studentProfile = profile.get();
            model.addAttribute("title", "top.menu.home");
            model.addAttribute("currentCourse", getCurrentCourse(studentProfile));
            session.setAttribute("groupId", studentProfile.getGroupId());
            return new ModelAndView("student/account", "profile", studentProfile);
        }
        return new ModelAndView("redirect:login");
    }

    private int getCurrentCourse(StudentProfile studentProfile) {
        return timeService.getCurrentDate().getYear() - studentProfile.getIncomeYear() + 1;
    }

    @ModelAttribute(value = "profile")
    public StudentProfile getProfile() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(auth.getName());
        return studentProfile.orElse(null);
    }

    @RequestMapping("/schedule")
    public String schedule(final Model model, @RequestParam(required = false) Integer week) {
        try {
            if(Objects.isNull(week))
                week = timeService.getCurrentEducationWeek();
            String groupCode = getProfile().getGroupId();
            String url = String.format(scheduleUrl, groupCode, week);

            Schedule schedule = new RestTemplate().getForObject(url, Schedule.class);

            Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> pairs = extractPairs(schedule);
            List<Schedule.Week.Day> days = schedule.getWeek().getDay();

            model.addAttribute("pairs", pairs);
            model.addAttribute("days", days);
            model.addAttribute("week", week);
        } catch (RuntimeException e) {
            //
        }
        model.addAttribute("title", "top.menu.schedule");
        return "student/schedule";
    }

    private Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> extractPairs(Schedule schedule) {
        Map<Integer, Map<Integer, Schedule.ScheduleElements.ScheduleElement>> pairs = new HashedMap(7);
        for(byte i = 0; i < 7; i++) {
            pairs.put(Integer.valueOf(i), new HashedMap(8));
        }
        for(Schedule.ScheduleElements.ScheduleElement scheduleElement : schedule.getScheduleElements().getScheduleElement()) {
            Map<Integer, Schedule.ScheduleElements.ScheduleElement> days = pairs.get(Integer.valueOf(scheduleElement.getPair()));
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
    public String contactUs(@RequestParam final String message, final HttpSession session) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //// TODO: 27.06.16 remove comments
        //simpleMailMessage.setFrom(getGroupId(session));
        simpleMailMessage.setFrom(scheduleUrl);
        simpleMailMessage.setTo(scheduleUrl);
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
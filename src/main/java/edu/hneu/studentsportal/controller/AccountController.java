package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping
    public ModelAndView account(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasAdminRole(auth.getAuthorities()))
            return new ModelAndView("redirect:management/uploadStudentProfilesFromExcel");
        return getUserProfileView(auth.getName(), session);
    }

    @RequestMapping("/schedule/{groupId}")
    public String schedule(@PathVariable String groupId, Model model) {
        model.addAttribute("groupId", groupId);
        return "schedule";
    }

    private boolean hasAdminRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private ModelAndView getUserProfileView(String email, HttpSession session) {
        Optional<StudentProfile> studentProfile = studentService.findStudentProfileByEmail(email);
        if (studentProfile.isPresent()) {
            return new ModelAndView("account", "profile", studentProfile.get());
        }
        return new ModelAndView("redirect:login");
    }
}
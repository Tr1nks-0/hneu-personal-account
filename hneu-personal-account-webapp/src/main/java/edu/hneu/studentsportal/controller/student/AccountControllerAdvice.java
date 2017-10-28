package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

import static org.apache.commons.lang.BooleanUtils.isFalse;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountControllerAdvice {

    private final StudentRepository studentRepository;
    private final CustomUserDetailsService userDetailsService;

    @ModelAttribute(value = "profile")
    public Student getProfile(HttpSession session, Principal principal) {
        Optional<String> email = Optional.ofNullable((String) session.getAttribute("email"));
        if (isFalse(email.isPresent()))
            email = Optional.ofNullable(principal).map(userDetailsService::extractUserEmail);
        return email.map(studentRepository::findByEmail).orElse(null);
    }
}

package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.controller.PdfGenerationController;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;
import edu.hneu.studentsportal.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;
import java.security.Principal;
import java.util.Map;

@Log4j
@Controller
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoadProfilePdfController implements PdfGenerationController {

    private final StudentRepository studentRepository;
    private final CustomUserDetailsService userDetailsService;
    private final PdfGenerationService pdfGenerationService;

    @GetMapping("/profile/pdf")
    public void loadProfile(Principal principal, HttpServletResponse response) {
        String email = userDetailsService.extractUserEmail(principal);
        Student student = studentRepository.findByEmail(email);
        writePdfToResponse(response, outputStream -> {
            Map<String, Object> variables = pdfGenerationService.prepareStudentProfile(student.getId());
            pdfGenerationService.write(variables, "pdf/student-profile", outputStream);
        });
    }

    @ExceptionHandler(ConnectException.class)
    public String handleError() {
        return "redirect:/account";
    }
}

package edu.hneu.studentsportal.controller.management.pdf;

import edu.hneu.studentsportal.controller.management.AbstractManagementController;
import edu.hneu.studentsportal.controller.PdfGenerationController;
import edu.hneu.studentsportal.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_STUDENTS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_STUDENTS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenerateStudentProfilePdfController extends AbstractManagementController implements PdfGenerationController {

    private final PdfGenerationService pdfGenerationService;

    @GetMapping("/{id}/pdf")
    public void getDisciplines(@PathVariable long id, HttpServletResponse response, Principal principal) {
        writePdfToResponse(response, outputStream -> {
            Map<String, Object> variables = pdfGenerationService.prepareStudentProfile(id);
            pdfGenerationService.write(variables, "pdf/student-profile", outputStream);
        });
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
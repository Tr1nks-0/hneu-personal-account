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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenerateSemesterStudentsMarksPdfReportController extends AbstractManagementController implements PdfGenerationController {

    private final PdfGenerationService pdfGenerationService;

    @GetMapping("/{groupId}/disciplines/marks/pdf")
    public void getDisciplines(@PathVariable Long groupId,
                               @RequestParam(defaultValue = "1") Integer course,
                               @RequestParam(defaultValue = "1") Integer semester,
                               HttpServletResponse response) {
        writePdfToResponse(response, outputStream -> {
            Map<String, Object> variables = pdfGenerationService.prepareStudentMarksReportPerSemester(groupId, course, semester);
            pdfGenerationService.write(variables, "pdf/student-marks-per-semester", outputStream);
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
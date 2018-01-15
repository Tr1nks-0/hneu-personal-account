package edu.hneu.studentsportal.controller.management;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupDisciplineMarksPdfManagementController extends AbstractManagementController {

    private final TemplateEngine templateEngine;
    private final GroupRepository groupRepository;

    @GetMapping("/{groupId}/disciplines/marks/pdf")
    public void getDisciplines(@PathVariable Long groupId,
                                 @RequestParam(defaultValue = "1") Long course,
                                 @RequestParam(defaultValue = "1") Long semester,
                                 HttpServletResponse response) throws IOException, DocumentException {
        Map<String, String> variables = new HashedMap<>();

        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        variables.put("faculty", group.getSpeciality().getFaculty().getName());
        variables.put("speciality", group.getSpeciality().getName());
        variables.put("educationProgram", group.getEducationProgram().getName());
        variables.put("semester", String.valueOf(semester));

        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process("pdf/student-marks", context);
        writeHtmlAsPdfToResponse(html, response);
    }

    private void writeHtmlAsPdfToResponse(String html, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        response.flushBuffer();
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
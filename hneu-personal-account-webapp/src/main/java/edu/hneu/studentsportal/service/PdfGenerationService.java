package edu.hneu.studentsportal.service;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfGenerationService {

    private final TemplateEngine templateEngine;

    @SneakyThrows
    public void write(Map<String, Object> variables, String template, ServletOutputStream outputStream) {
        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process(template, context);
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }
}

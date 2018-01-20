package edu.hneu.studentsportal.controller.management.pdf;

import lombok.SneakyThrows;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

public interface PdfGenerationController {

    @SneakyThrows
    default void writePdfToResponse(HttpServletResponse response, Consumer<ServletOutputStream> consumer) {
        response.setContentType("application/pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            consumer.accept(outputStream);
        } catch (Exception e) {
            outputStream.close();
            response.flushBuffer();
        }
    }
}
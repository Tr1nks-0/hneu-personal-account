package edu.hneu.studentsportal.controller.management.pdf;

import lombok.SneakyThrows;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

public interface PdfGenerationController {

    @SneakyThrows
    default void writePdfToResponse(HttpServletResponse response, Consumer<ServletOutputStream> consumer) {
        response.setContentType("application/pdf");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            consumer.accept(outputStream);
        } finally {
            response.flushBuffer();
        }
    }
}
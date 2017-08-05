package edu.hneu.studentsportal.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception e) throws Exception {
        log.warn(e.getMessage(), e);
        return "error-page";
    }
}
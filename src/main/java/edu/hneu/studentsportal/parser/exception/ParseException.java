package edu.hneu.studentsportal.parser.exception;

import static java.lang.String.format;

import lombok.Data;

@Data
public class ParseException extends RuntimeException{

    private String errorCode;

    public ParseException(String errorCode) {
        super(format("Parsing failed due to error with code[%s]", errorCode));
        this.errorCode = errorCode;
    }
}

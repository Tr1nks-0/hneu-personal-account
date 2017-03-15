package edu.hneu.studentsportal.parser.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParseErrorCodes {

    public static final String NOT_SUPPORTED_FORMAT = "1";
    public static final String INVALID_CONTENT = "2";
    public static final String GROUP_WAS_NOT_FOUND = "3";
    public static final String STUDENT_PHOTO_NOT_FOUND = "4";
    public static final String EMPTY_COURSES = "4";
    public static final String COURSE_WITHOUT_SEMESTERS = "4";
    public static final String SEMESTER_WITHOUT_DISCIPLINES = "4";
    public static final String INVALID_DISCIPLINE_NAME = "4";
}

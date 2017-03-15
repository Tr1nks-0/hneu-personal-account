package edu.hneu.studentsportal.parser.util.validation;


import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;

import edu.hneu.studentsportal.entity.Course;
import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.Semester;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.parser.exception.ParseErrorCodes;
import edu.hneu.studentsportal.parser.exception.ParseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentProfileValidationUtils {

    private static final String VALID_HEADER_HOLDER = "ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН";

    public static void validateStudentProfileFile(String header) {
        if (isFalse(header.contains(VALID_HEADER_HOLDER)))
            throw new ParseException(ParseErrorCodes.INVALID_CONTENT);
    }

    public static void validateCourses(List<Course> courses) {
        if(isEmpty(courses))
            throw new ParseException(ParseErrorCodes.EMPTY_COURSES);
    }

    public static void validateCourse(Course course) {
        if(isEmpty(course.getSemesters()))
            throw new ParseException(ParseErrorCodes.COURSE_WITHOUT_SEMESTERS);
    }

    public static void validateSemester(Semester semester) {
        if(isEmpty(semester.getDisciplines()))
            throw new ParseException(ParseErrorCodes.SEMESTER_WITHOUT_DISCIPLINES);
    }

    public static void validateDiscipline(Discipline discipline) {
        if(discipline.getType() == DisciplineType.REGULAR && isBlank(discipline.getLabel()))
            throw new ParseException(ParseErrorCodes.INVALID_DISCIPLINE_NAME);
    }
}

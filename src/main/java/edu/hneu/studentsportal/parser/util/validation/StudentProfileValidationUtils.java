package edu.hneu.studentsportal.parser.util.validation;


import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.parser.exception.ParseErrorCodes;
import edu.hneu.studentsportal.parser.exception.ParseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentProfileValidationUtils {

    private static final String VALID_HEADER_HOLDER = "ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН";

    public static void validateStudentProfileFile(String header) {
        if (isFalse(header.contains(VALID_HEADER_HOLDER)))
            throw new ParseException(ParseErrorCodes.INVALID_CONTENT);
    }


    public static void validateDiscipline(Discipline discipline) {
        if(discipline.getType() == DisciplineType.REGULAR && isBlank(discipline.getLabel()))
            throw new ParseException(ParseErrorCodes.INVALID_DISCIPLINE_NAME);
    }

}

package edu.hneu.studentsportal.parser.factory;

import edu.hneu.studentsportal.parser.impl.DisciplinesExcelParser;
import edu.hneu.studentsportal.parser.impl.StudentMarksExcelParser;
import edu.hneu.studentsportal.parser.impl.StudentsChoiceExcelParser;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class ParserFactory {

    @Lookup
    public abstract StudentMarksExcelParser newStudentMarksExcelParser();

    @Lookup
    public abstract StudentsChoiceExcelParser newStudentsChoiceParser();

    @Lookup
    public abstract DisciplinesExcelParser newDisciplinesParser();

}

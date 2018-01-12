package edu.hneu.studentsportal.parser.factory;

import edu.hneu.studentsportal.parser.impl.DisciplinesExcelParser;
import edu.hneu.studentsportal.parser.impl.StudentsChoiceExcelParser;
import edu.hneu.studentsportal.parser.impl.StudentsExcelParser;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class ParserFactory {

    @Lookup
    public abstract StudentsChoiceExcelParser newStudentsChoiceParser();

    @Lookup
    public abstract DisciplinesExcelParser newDisciplinesParser();

    @Lookup
    public abstract StudentsExcelParser newStudentsParser();
}

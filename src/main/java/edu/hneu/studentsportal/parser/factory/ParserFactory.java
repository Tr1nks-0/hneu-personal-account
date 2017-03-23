package edu.hneu.studentsportal.parser.factory;

import edu.hneu.studentsportal.parser.impl.StudentMarksExcelParser;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.parser.impl.StudentProfileExcelParser;

@Component
public abstract class ParserFactory {

    @Lookup
    public abstract StudentProfileExcelParser newStudentProfileExcelParser();

    @Lookup
    public abstract StudentMarksExcelParser newStudentMarksExcelParser();
}

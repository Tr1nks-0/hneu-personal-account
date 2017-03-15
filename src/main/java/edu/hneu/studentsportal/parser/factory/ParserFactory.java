package edu.hneu.studentsportal.parser.factory;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.parser.impl.StudentProfileExcelParser;

@Component
public abstract class ParserFactory {

    @Lookup
    public abstract StudentProfileExcelParser newStudentProfileExcelParser();
}

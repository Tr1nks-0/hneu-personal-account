package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.parser.factory.ParserFactory;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportService {

    private final ParserFactory parserFactory;
    private final DisciplineRepository disciplineRepository;

    public Map<Student, List<Discipline>> importStudentsChoice(File file) {
        return parserFactory.newStudentsChoiceParser().parse(file);
    }

    public List<Discipline> importDisciplines(File file) {
        List<Discipline> disciplines = parserFactory.newDisciplinesParser().parse(file);
        disciplineRepository.save(disciplines);
        disciplines.forEach(discipline -> log.info(format("New discipline[%s] was imported", discipline)));
        return disciplines;
    }

}

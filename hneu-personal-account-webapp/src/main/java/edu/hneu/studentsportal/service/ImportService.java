package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.parser.factory.ParserFactory;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportService {

    private final ParserFactory parserFactory;
    private final DisciplineRepository disciplineRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;

    public Map<Student, List<Discipline>> importStudentsChoice(File file) {
        return parserFactory.newStudentsChoiceParser().parse(file);
    }

    public List<Discipline> importDisciplines(File file) {
        List<Discipline> disciplines = parserFactory.newDisciplinesParser().parse(file);
        disciplineRepository.save(disciplines);
        disciplines.forEach(discipline -> log.info(format("New %s was imported", discipline)));
        return disciplines;
    }

    public Map<String, Student> importStudents(File file) {
        Map<String, Student> students = parserFactory.newStudentsParser().parse(file);
        List<Student> studentsToSave = students.values().stream().filter(Objects::nonNull).collect(toList());
        studentRepository.save(studentsToSave);
        studentsToSave.forEach(student -> {
            userService.create(student.getEmail(), UserRole.STUDENT);
            log.info(format("New %s was imported", student));
        });
        return students;
    }

}

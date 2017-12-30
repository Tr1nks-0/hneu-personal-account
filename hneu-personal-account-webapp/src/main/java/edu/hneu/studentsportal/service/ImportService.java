package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.parser.factory.ParserFactory;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportService {

    private final ParserFactory parserFactory;
    private final StudentRepository studentRepository;
    private final DisciplineMarkService disciplineMarkService;
    private final DisciplineRepository disciplineRepository;

    public Map<Student, List<Discipline>> importStudentsChoice(File file) {
        Map<Student, List<Discipline>> studentsChoice = parserFactory.newStudentsChoiceParser().parse(file);
        studentsChoice.forEach((student, disciplines) -> {
            List<DisciplineMark> newMarks = createNewMarksFromStudentChoice(student, disciplines);
            student.getDisciplineMarks().addAll(newMarks);
            log.info(format("New marks[%s] were added to student[%s]", newMarks, student));
        });
        studentRepository.save(studentsChoice.keySet());
        return studentsChoice;
    }

    public List<Discipline> importDisciplines(File file) {
        List<Discipline> disciplines = parserFactory.newDisciplinesParser().parse(file);
        disciplineRepository.save(disciplines);
        disciplines.forEach(discipline -> log.info(format("New discipline[%s] was imported", discipline)));
        return disciplines;
    }

    private List<DisciplineMark> createNewMarksFromStudentChoice(Student student, List<Discipline> disciplines) {
        List<Discipline> studentDisciplines = disciplineMarkService.extract(student.getDisciplineMarks(), DisciplineMark::getDiscipline);
        Predicate<Discipline> doesStudentHaveDiscipline = discipline -> isFalse(studentDisciplines.contains(discipline));
        return disciplines.stream().filter(doesStudentHaveDiscipline).map(DisciplineMark::new).collect(toList());
    }

}

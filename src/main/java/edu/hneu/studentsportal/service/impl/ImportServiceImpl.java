package edu.hneu.studentsportal.service.impl;


import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.parser.factory.ParserFactory;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.ImportService;
import edu.hneu.studentsportal.service.StudentEmailReceivingService;
import edu.hneu.studentsportal.service.UserService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class ImportServiceImpl implements ImportService {

    @Resource
    private ParserFactory parserFactory;
    @Resource
    private UserService userService;
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private DisciplineMarkService disciplineMarkService;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private StudentEmailReceivingService studentEmailReceivingService;

    @Override
    public Student importStudent(File file) {
        Student student = parserFactory.newStudentProfileExcelParser().parse(file);
        student.setEmail(studentEmailReceivingService.receiveStudentEmail(student));
        userService.create(student.getEmail(), UserRole.STUDENT);
        studentRepository.save(student);
        log.info(format("New student[%s] has been created.", student));
        return student;
    }

    @Override
    public Map<Student, List<DisciplineMark>> importStudentMarks(File file) {
        Map<Student, List<DisciplineMark>> importedStudentsMarks = parserFactory.newStudentMarksExcelParser().parse(file);
        Map<Student, List<DisciplineMark>> updatedStudentsMarks = new HashMap<>();
        importedStudentsMarks.forEach((student, importedMarks) -> {
            List<DisciplineMark> updatedMarks = disciplineMarkService.updateStudentMarks(student, importedMarks);
            if (CollectionUtils.isNotEmpty(updatedMarks)) {
                studentRepository.save(student);
                updatedStudentsMarks.put(student, updatedMarks);
            }
        });
        return updatedStudentsMarks;
    }

    @Override
    public Map<Student, List<Discipline>> importStudentsChoice(File file) {
        Map<Student, List<Discipline>> studentsChoice = parserFactory.newStudentsChoiceParser().parse(file);
        studentsChoice.forEach((student, disciplines) -> {
            List<DisciplineMark> newMarks = createNewMarksFromStudentChoice(student, disciplines);
            student.getDisciplineMarks().addAll(newMarks);
        });
        studentRepository.save(studentsChoice.keySet());
        return studentsChoice;
    }

    @Override
    public List<Discipline> importDisciplines(File file) {
        List<Discipline> disciplines = parserFactory.newDisciplinesParser().parse(file);
        disciplineRepository.save(disciplines);
        disciplines.forEach(discipline -> log.info(format("New discipline[%s] imported", discipline)));
        return disciplines;
    }

    private List<DisciplineMark> createNewMarksFromStudentChoice(Student student, List<Discipline> disciplines) {
        List<Discipline> studentMarks = disciplineMarkService.extract(student.getDisciplineMarks(), DisciplineMark::getDiscipline);
        Predicate<DisciplineMark> doesStudentHaveMark = mark -> isFalse(studentMarks.contains(mark.getDiscipline()));
        List<DisciplineMark> newMarks = disciplines.stream().map(DisciplineMark::new).collect(toList());
        return newMarks.stream().filter(doesStudentHaveMark).collect(toList());
    }


}

package edu.hneu.studentsportal.parser.impl;

import com.google.common.collect.Maps;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import javaslang.control.Try;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Log4j
@Component
@Scope("prototype")
public class StudentsChoiceExcelParser extends AbstractExcelParser<Map<Student, List<Discipline>>> {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private GroupRepository groupRepository;
    @Resource
    private DisciplineRepository disciplineRepository;

    @Override
    public Map<Student, List<Discipline>> extractModel() {
        Map<Student, List<Discipline>> studentsChoice = Maps.newHashMap();
        Indexer indexer = Indexer.of(0);

        validateStudentsChoiceExcelFile(indexer.getValue());

        while (isNotFileEnd(indexer.next())) {
            Try.run(() -> {
                Group group = findGroup(indexer);
                Student student = findStudent(indexer, group);
                Discipline discipline = findDiscipline(indexer, group);
                putOrUpdateStudentsChoice(studentsChoice, student, discipline);
            }).onFailure(e -> log.warn(e.getMessage(), e));
        }
        return studentsChoice;
    }

    private void validateStudentsChoiceExcelFile(int row) {
        validateHeaders(row, newArrayList("Прізвище", "Ім'я", "По батькові", "№ академічної групи", "Код", "Курс", "Семестр"));
    }

    private boolean isNotFileEnd(int row) {
        return isFalse(isEmpty(getString1CellValue(row)));
    }

    private Group findGroup(Indexer indexer) {
        String groupName = getStringCellValue(indexer, 3);
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalStateException("Cannot find group for name: " + groupName));
    }

    private Student findStudent(Indexer indexer, Group group) {
        String surname = getStringCellValue(indexer);
        String fullName = getString1CellValue(indexer) + " " + getString2CellValue(indexer);
        return studentRepository.findByNameAndSurnameAndGroup(fullName, surname, group)
                .orElseThrow(() -> new IllegalStateException(format("Cannot find student for full name[%s %s] and group[%s]", fullName, surname, group.getName())));
    }

    private Discipline findDiscipline(Indexer indexer, Group group) {
        String code = getStringCellValue(indexer, 4);
        int course = getIntegerCellValue(indexer, 5);
        int semester = getIntegerCellValue(indexer, 6);
        return disciplineRepository.findByCodeAndCourseAndSemesterAndSpecialityAndEducationProgram(code, course, semester, group.getSpeciality(), group.getEducationProgram())
                .orElseThrow(() -> new IllegalStateException(format("Cannot find discipline for code:", code)));
    }

    private void putOrUpdateStudentsChoice(Map<Student, List<Discipline>> choice, Student student, Discipline discipline) {
        if (isFalse(choice.containsKey(student)))
            choice.put(student, newArrayList());
        List<Discipline> studentChoice = choice.get(student);
        studentChoice.add(discipline);
        choice.put(student, studentChoice);
    }
}

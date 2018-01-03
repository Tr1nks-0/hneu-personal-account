package edu.hneu.studentsportal.parser.impl;

import com.google.common.collect.Maps;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.*;
import static java.lang.String.format;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Component
@Scope("prototype")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentsChoiceExcelParser extends AbstractExcelParser<Map<Student, List<Discipline>>> {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final DisciplineRepository disciplineRepository;

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
        validateHeaders(row, newArrayList("Прізвище", "Ім'я", "По батькові", "№ академічної групи", "Код", "Назва", "Курс", "Семестр", "Маг-майнер"));
    }

    private boolean isNotFileEnd(int row) {
        return isFalse(isEmpty(getString1CellValue(row)));
    }

    private Group findGroup(Indexer indexer) {
        String groupName = getStringCellValue(indexer, 3);
        Optional<Group> group = groupRepository.findByName(groupName);
        return group.orElseThrow(() -> new IllegalStateException("Cannot find group for name: " + groupName));
    }

    private Student findStudent(Indexer indexer, Group group) {
        String surname = getStringCellValue(indexer);
        String fullName = getString1CellValue(indexer) + " " + getString2CellValue(indexer);
        return studentRepository.findByNameAndSurnameAndGroup(fullName, surname, group)
                .orElseThrow(() -> new IllegalStateException(format("Cannot find student for full name[%s %s] and group[%s]", fullName, surname, group.getName())));
    }

    private Discipline findDiscipline(Indexer indexer, Group group) {
        String code = getStringCellValue(indexer, 4);
        int course = getIntegerCellValue(indexer, 6);
        int semester = getIntegerCellValue(indexer, 7);
        Speciality speciality = group.getSpeciality();
        EducationProgram educationProgram = group.getEducationProgram();
        Discipline discipline = findDiscipline(code, course, semester, speciality, educationProgram);
        String label = getStringCellValue(indexer, 5);
        if (StringUtils.equalsIgnoreCase(discipline.getLabel(), label))
            return discipline;
        else
            return createNewDiscipline(discipline, label);
    }

    private Discipline createNewDiscipline(Discipline discipline, String label) {
        Discipline newDiscipline = Discipline.builder()
                .code(discipline.getCode())
                .label(label)
                .credits(discipline.getCredits())
                .course(discipline.getCourse())
                .semester(discipline.getSemester())
                .educationProgram(discipline.getEducationProgram())
                .speciality(discipline.getSpeciality())
                .controlForm(discipline.getControlForm())
                .type(discipline.getType())
                .build();
        disciplineRepository.save(newDiscipline);
        log.info(format("New discipline[%s] was created.", newDiscipline));
        return newDiscipline;
    }

    private Discipline findDiscipline(String code, int course, int semester, Speciality speciality, EducationProgram educationProgram) {
        Specifications<Discipline> spec = where(hasCode(code)
                .and(hasCourseAndSemester(course, semester))
                .and(hasSpeciality(speciality))
                .and(hasEducationProgram(educationProgram)));
        return Optional.ofNullable(disciplineRepository.findOne(spec))
                .orElseThrow(() -> new IllegalStateException(format("Cannot find discipline: code=%s, course=%s, semester=%s, speciality=%s, educationProgram=%s",
                        code, course, semester, speciality, educationProgram)));
    }

    private void putOrUpdateStudentsChoice(Map<Student, List<Discipline>> choice, Student student, Discipline discipline) {
        if (isFalse(choice.containsKey(student)))
            choice.put(student, newArrayList());
        List<Discipline> studentChoice = choice.get(student);
        studentChoice.add(discipline);
        choice.put(student, studentChoice);
    }
}

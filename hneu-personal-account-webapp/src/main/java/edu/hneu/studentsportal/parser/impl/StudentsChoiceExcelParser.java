package edu.hneu.studentsportal.parser.impl;

import com.google.common.collect.Maps;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.StudentService;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
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
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Component
@Scope("prototype")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentsChoiceExcelParser extends AbstractExcelParser<Map<Student, List<Discipline>>> {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
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
                Discipline discipline = getDiscipline(indexer, group);
                updateStudentMarks(indexer, student, discipline);
                updateStudentsChoice(studentsChoice, student, discipline);
            }).onFailure(e -> log.warn(e.getMessage(), e));
        }
        return studentsChoice;
    }


    private void validateStudentsChoiceExcelFile(int row) {
        validateHeaders(row, newArrayList("Прізвище", "Ім'я", "По батькові", "№ академічної групи", "Код", "Назва", "Курс", "Семестр", "Тип"));
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

    private Discipline getDiscipline(Indexer indexer, Group group) {
        String code = getStringCellValue(indexer, 4);
        int course = getIntegerCellValue(indexer, 6);
        int semester = getIntegerCellValue(indexer, 7);
        Speciality speciality = group.getSpeciality();
        EducationProgram educationProgram = group.getEducationProgram();

        Optional<Discipline> discipline = getDiscipline(code, course, semester, speciality, educationProgram);
        if (discipline.isPresent()) {
            return discipline.get();
        } else {
            String tempDisciplineCode = getStringCellValue(indexer, 8);
            Discipline tempDiscipline = getDiscipline(tempDisciplineCode, course, semester, speciality, educationProgram)
                    .orElseThrow(() -> new IllegalStateException(format("Cannot find discipline: code=%s, course=%s, semester=%s, speciality=%s, educationProgram=%s",
                            tempDisciplineCode, course, semester, speciality, educationProgram)));
            return createSecondaryDiscipline(tempDiscipline, code, getStringCellValue(indexer, 5));
        }
    }

    private Discipline createSecondaryDiscipline(Discipline tempDiscipline, String code, String label) {
        Discipline secondaryDiscipline = Discipline.builder()
                .code(code)
                .label(label)
                .credits(tempDiscipline.getCredits())
                .course(tempDiscipline.getCourse())
                .semester(tempDiscipline.getSemester())
                .educationProgram(tempDiscipline.getEducationProgram())
                .speciality(tempDiscipline.getSpeciality())
                .controlForm(tempDiscipline.getControlForm())
                .type(tempDiscipline.getType())
                .secondary(true)
                .build();
        disciplineRepository.save(secondaryDiscipline);
        log.info(format("New discipline[%s] was created.", secondaryDiscipline));
        return secondaryDiscipline;
    }

    private Optional<Discipline> getDiscipline(String code, int course, int semester, Speciality speciality, EducationProgram educationProgram) {
        Specifications<Discipline> spec = where(hasCode(code)
                .and(hasCourseAndSemester(course, semester))
                .and(hasSpeciality(speciality))
                .and(hasEducationProgram(educationProgram)));
        return Optional.ofNullable(disciplineRepository.findOne(spec));
    }

    private void updateStudentMarks(Indexer indexer, Student student, Discipline discipline) {
        if (discipline.isSecondary()) {
            String tempDisciplineCode = getStringCellValue(indexer, 8);
            DisciplineType disciplineType = DisciplineType.of(tempDisciplineCode.substring(0, tempDisciplineCode.length() - 1));
            Integer number = Integer.valueOf(String.valueOf(tempDisciplineCode.charAt(tempDisciplineCode.length() - 1)));
            findStudentMarkWithTemporalDiscipline(student, disciplineType, number - 1).ifPresent(mark -> mark.setDiscipline(discipline));
        } else if (studentHasNotSuchDiscipline(student, discipline)) {
            DisciplineMark newMark = new DisciplineMark(discipline, student);
            log.info(format("New mark[%s] was added to student[%s]", newMark, student));
            student.getDisciplineMarks().add(newMark);
        }
        studentService.save(student);
    }

    private Optional<DisciplineMark> findStudentMarkWithTemporalDiscipline(Student student, DisciplineType disciplineType, Integer index) {
        List<DisciplineMark> disciplinesByType = student.getDisciplineMarks().stream()
                .filter(mark -> mark.getDiscipline().getType().equals(disciplineType))
                .collect(toList());
        return disciplinesByType.size() > index ? Optional.of(disciplinesByType.get(index)) : Optional.empty();
    }

    private boolean studentHasNotSuchDiscipline(Student student, Discipline discipline) {
        return student.getDisciplineMarks().stream()
                .noneMatch(mark -> mark.getDiscipline().getCode().equalsIgnoreCase(discipline.getCode()));
    }

    private void updateStudentsChoice(Map<Student, List<Discipline>> choice, Student student, Discipline discipline) {
        if (isFalse(choice.containsKey(student))) {
            choice.put(student, newArrayList());
        }
        List<Discipline> studentChoice = choice.get(student);
        studentChoice.add(discipline);
        choice.put(student, studentChoice);
    }
}

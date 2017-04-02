package edu.hneu.studentsportal.parser.impl;

import com.google.api.client.util.Lists;
import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.math.NumberUtils.isNumber;

@Log4j
@Component
@Scope("prototype")
public class StudentMarksExcelParser extends AbstractExcelParser<Map<Student, List<DisciplineMark>>> {

    private static final int MAX_NUMBER_OF_ITERATIONS = 100;
    private static final int GROUP_ID_ROW_INDEX = 5;
    private static final int SEMESTER_NUMBER_ROW_INDEX = 3;
    private static final int GROUP_NAME_CELL = 8;
    private static final int START_DISCIPLINES_ROW = 8;
    private static final int START_DISCIPLINES_COL = 4;

    private static final String NUMBER_MARKER = "№";
    private static final String AVERAGE_SCORE_MARKER = "Середній бал";
    private static final String GROUP_PREFIX = "групи:";
    private static final String SEMESTER_NUMBER_PREFIX = "Навчальний семестр:";
    private static final String FILE_END_PREFIX = "Декан факультету";
    private static final String DISCIPLINES_HEADER = "Cередній бал за дисциплінами";

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private StudentRepository studentRepository;

    @Override
    public Map<Student, List<DisciplineMark>> extractModel() {
        if (isFalse(getStringCellValue(7, 4).contains(DISCIPLINES_HEADER)))
            throw new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.marks.file", new Object[0], Locale.getDefault()));

        List<Student> students = getStudents();
        List<Discipline> disciplines = getDisciplines();

        int row = getStartRow();
        Map<Student, List<DisciplineMark>> studentsDisciplineMarks = new HashMap<>();
        while (isNotEndFile(row)) {
            String fullName = getString1CellValue(row);
            if (isNotBlank(fullName)) {
                Student student = getStudentByFullName(fullName, students);
                studentsDisciplineMarks.put(student, getDisciplineMarks(row, disciplines));
            }
            row++;
        }
        return studentsDisciplineMarks;
    }

    private List<Student> getStudents() {
        return studentRepository.findByStudentGroup(getGroup());
    }

    private Group getGroup() {
        final String groupName = getStringCellValue(GROUP_ID_ROW_INDEX, GROUP_NAME_CELL).toLowerCase().replace(GROUP_PREFIX, StringUtils.EMPTY).trim();
        return groupRepository.findByName(groupName).orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.marks.group.not.found", new Object[]{groupName}, Locale.getDefault())));
    }

    private List<Discipline> getDisciplines() {
        List<Discipline> disciplines = Lists.newArrayList();
        for (int i = START_DISCIPLINES_COL; isEndOfDisciplines(i); i++) {
            disciplines.add(getDiscipline(getStringCellValue(START_DISCIPLINES_ROW, i)));
        }
        return disciplines;
    }

    private boolean isEndOfDisciplines(int i) {
        return i < MAX_NUMBER_OF_ITERATIONS && isFalse(getStringCellValue(START_DISCIPLINES_ROW - 1, i).contains(AVERAGE_SCORE_MARKER));
    }

    private boolean isNotEndFile(int row) {
        return row < MAX_NUMBER_OF_ITERATIONS && isFalse(getString1CellValue(row).contains(FILE_END_PREFIX));
    }

    private Student getStudentByFullName(String fullName, List<Student> groupStudents) {
        Optional<Student> student = groupStudents.stream().filter(s -> fullName.contains(s.getName())).filter(s -> fullName.contains(s.getSurname())).findFirst();
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.marks.student.not.found", new Object[]{fullName}, Locale.getDefault()));
        }
    }

    private List<DisciplineMark> getDisciplineMarks(int row, List<Discipline> disciplines) {
        return IntStream.range(START_DISCIPLINES_COL, disciplines.size() + START_DISCIPLINES_COL)
                .filter(isDisciplineMark(row))
                .mapToObj(getDisciplineMark(row, disciplines))
                .collect(Collectors.toList());
    }

    private IntPredicate isDisciplineMark(int row) {
        return i -> isNumber(getStringCellValue(row, i));
    }

    private Discipline getDiscipline(String disciplineName) {
        return disciplineRepository.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(disciplineName, getCourse(), getSemester(), getGroup().getSpeciality(), getGroup().getEducationProgram())
                .orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.marks.discipline.not.found", new Object[]{disciplineName}, Locale.getDefault())));
    }

    private IntFunction<DisciplineMark> getDisciplineMark(int row, List<Discipline> disciplines) {
        return col -> {
            String mark = getStringCellValue(row, col);
            return new DisciplineMark(disciplines.get(col - START_DISCIPLINES_COL), mark);
        };
    }

    private int getStartRow() {
        return IntStream.range(0, MAX_NUMBER_OF_ITERATIONS)
                .filter(i -> getStringCellValue(i).contains(NUMBER_MARKER))
                .map(i -> i + 2)
                .findFirst().orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.marks.file", new Object[0], Locale.getDefault())));
    }

    private int getSemester() {
        return Integer.parseInt(getString2CellValue(SEMESTER_NUMBER_ROW_INDEX).replace(SEMESTER_NUMBER_PREFIX, StringUtils.EMPTY).trim());
    }

    private int getCourse() {
        return Integer.parseInt(getStringCellValue(6).split(" ")[1]);
    }

}

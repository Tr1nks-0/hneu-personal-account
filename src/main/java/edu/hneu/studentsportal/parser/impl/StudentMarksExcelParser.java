package edu.hneu.studentsportal.parser.impl;

import com.google.api.client.util.Lists;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.conditions.DisciplineConditions;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
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
    @Resource
    private DisciplineMarkService disciplineMarkService;

    @Override
    public Map<Student, List<DisciplineMark>> extractModel() {
        if (isFalse(getStringCellValue(7, 4).contains(DISCIPLINES_HEADER)))
            throw new IllegalArgumentException(messageService.invalidStudentMarksError());

        List<Student> students = studentRepository.findByGroup(getGroup());
        List<Discipline> disciplines = getDisciplines();

        int row = getStartRow();
        Map<Student, List<DisciplineMark>> studentsDisciplineMarks = new HashMap<>();
        while (isNotEndFile(row)) {
            String fullName = getString1CellValue(row);
            Optional<Student> studentOptional = getStudentByFullName(fullName, students);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                List<DisciplineMark> disciplineMarks = disciplineMarkService.alignStudentDisciplinesMark(student, getDisciplineMarks(row, disciplines));
                studentsDisciplineMarks.put(student, disciplineMarks);
            }
            row++;
        }
        return studentsDisciplineMarks;
    }

    private Group getGroup() {
        final String groupName = getStringCellValue(GROUP_ID_ROW_INDEX, GROUP_NAME_CELL).toLowerCase().replace(GROUP_PREFIX, StringUtils.EMPTY).trim();
        return groupRepository.findByName(groupName).orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentGroupError(groupName)));
    }

    private List<Discipline> getDisciplines() {
        List<Discipline> disciplines = Lists.newArrayList();
        for (int i = START_DISCIPLINES_COL; isEndOfDisciplines(i); i++) {
            String disciplineName = getStringCellValue(START_DISCIPLINES_ROW, i);
            disciplines.add(getDiscipline(disciplineName));
        }
        return disciplines;
    }

    private boolean isEndOfDisciplines(int i) {
        return i < MAX_NUMBER_OF_ITERATIONS && isFalse(getStringCellValue(START_DISCIPLINES_ROW - 1, i).contains(AVERAGE_SCORE_MARKER));
    }

    private Discipline getDiscipline(String disciplineName) {
        if (DisciplineConditions.isMasterDisciplineLabel(disciplineName))
            return new Discipline(disciplineName, DisciplineType.MAGMAYNOR, getCourse(), getSemester());
        else
            return findDiscipline(disciplineName).orElseThrow(() -> new IllegalArgumentException(messageService.disciplineNotFoundError(disciplineName)));
    }

    private Optional<Discipline> findDiscipline(String disciplineName) {
        return disciplineRepository.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(disciplineName, getCourse(), getSemester(),
                getGroup().getSpeciality(), getGroup().getEducationProgram());
    }

    private int getCourse() {
        return Integer.parseInt(getStringCellValue(6).split(" ")[1]);
    }

    private int getSemester() {
        return Integer.parseInt(getString2CellValue(SEMESTER_NUMBER_ROW_INDEX).replace(SEMESTER_NUMBER_PREFIX, StringUtils.EMPTY).trim());
    }

    private int getStartRow() {
        return IntStream.range(0, MAX_NUMBER_OF_ITERATIONS)
                .filter(i -> getStringCellValue(i).contains(NUMBER_MARKER))
                .map(i -> i + 2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(messageService.invalidFile()));
    }

    private boolean isNotEndFile(int row) {
        return row < MAX_NUMBER_OF_ITERATIONS && isFalse(getString1CellValue(row).contains(FILE_END_PREFIX));
    }

    private Optional<Student> getStudentByFullName(String fullName, List<Student> groupStudents) {
        if (isNotBlank(fullName))
            return groupStudents.stream()
                    .filter(s -> fullName.contains(s.getName()))
                    .filter(s -> fullName.contains(s.getSurname()))
                    .findFirst();
        return Optional.empty();
    }

    private List<DisciplineMark> getDisciplineMarks(int row, List<Discipline> disciplines) {
        return IntStream.range(START_DISCIPLINES_COL, disciplines.size() + START_DISCIPLINES_COL)
                .filter(isDisciplineMark(row))
                .mapToObj(getDisciplineMark(row, disciplines))
                .collect(toList());
    }

    private IntPredicate isDisciplineMark(int row) {
        return i -> isNumber(getStringCellValue(row, i));
    }

    private IntFunction<DisciplineMark> getDisciplineMark(int row, List<Discipline> disciplines) {
        return col -> {
            String mark = getStringCellValue(row, col);
            return new DisciplineMark(disciplines.get(col - START_DISCIPLINES_COL), mark);
        };
    }

}

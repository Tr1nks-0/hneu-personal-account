package edu.hneu.studentsportal.parser.impl;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
@Scope("prototype")
public class StudentMarksExcelParser extends AbstractExcelParser<Map<Student,List<DisciplineMark>>> {

    private static final int MAX_NUMBER_OF_ITERATIONS = 100;
    private static final int GROUP_ID_ROW_INDEX = 5;
    private static final int SEMESTER_NUMBER_ROW_INDEX = 3;
    private static final String NUMBER_MARKER = "№";
    private static final String FIRST_DISCIPLINE_MARKER = "Фін";
    private static final String AVERAGE_SCORE_MARKER = "Середній бал";
    private static final String INVALID_SYSTEM_MARKER = "(5б)";
    private static final String GROUP_PREFIX = "групи:";
    private static final String SEMESTER_NUMBER_PREFIX = "Навчальний семестр:";
    private static final int GROUP_NAME_CELL = 8;

    private int currentIndex = 0;

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private StudentRepository studentRepository;

    @Override
    public Map<Student, List<DisciplineMark>> extractModel() {
        Map<Student, List<DisciplineMark>> studentsDisciplinesScores = new HashMap<>();

        List<Student> students = studentRepository.findByGroup(getGroup());

        int headerIndex = getCurrentIndex();
        int firstDisciplineIndex = getFirstDisciplineNameIndex(headerIndex - 1);
        for (currentIndex = headerIndex + 2; isaBoolean(); currentIndex++) {
            final String fullName = getString1CellValue(currentIndex);
            if (isNotBlank(fullName)) {
                Student student = getStudentByFullName(students, fullName);
                studentsDisciplinesScores.put(student, getScoresForDisciplines(headerIndex, firstDisciplineIndex));
            }
        }
        return studentsDisciplinesScores;
    }

    private boolean isaBoolean() {
        return currentIndex < MAX_NUMBER_OF_ITERATIONS && !getString1CellValue(currentIndex).contains("Декан факультету економічної інформатики");
    }

    private Student getStudentByFullName(List<Student> groupStudents, String fullName) {
        return groupStudents.stream()
                .filter(s -> fullName.contains(s.getName()))
                .filter(s -> fullName.contains(s.getSurname()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }


    private List<DisciplineMark> getScoresForDisciplines(final int headerIndex, final int firstDisciplineNameIndex) {
        final List<DisciplineMark> mapMarks = new ArrayList<>();
        for (int j = firstDisciplineNameIndex; j < MAX_NUMBER_OF_ITERATIONS; j++) {
            if (getStringCellValue(headerIndex - 1, j).contains(AVERAGE_SCORE_MARKER))
                break;
            final String disciplineScore = getStringCellValue(currentIndex, j);
            if(NumberUtils.isNumber(disciplineScore)) {
                final String disciplineName = getStringCellValue(headerIndex, j);
                final Discipline discipline = disciplineRepository.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(disciplineName, getCourse(), getSemester(), getGroup().getSpeciality(), getGroup().getEducationProgram()).orElseThrow(() -> new RuntimeException());
                mapMarks.add(new DisciplineMark(discipline, Double.valueOf(disciplineScore)));
            }
        }
        return mapMarks;
    }

    private int getCurrentIndex() {
        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++)
            if (getStringCellValue(i, 0).contains(NUMBER_MARKER))
                return ++i;
        throw new RuntimeException();
    }

    private int getFirstDisciplineNameIndex(final int headerIndex) {
        for (int j = 0; j < MAX_NUMBER_OF_ITERATIONS; j++)
            if (getStringCellValue(headerIndex, j).contains(FIRST_DISCIPLINE_MARKER))
                return ++j;
        throw new RuntimeException();
    }


    private Group getGroup() {
        final String groupName = getStringCellValue(GROUP_ID_ROW_INDEX, GROUP_NAME_CELL).toLowerCase().replace(GROUP_PREFIX, StringUtils.EMPTY).trim();
        return groupRepository.findByName(groupName).orElseThrow(() -> new RuntimeException());
    }

    private int getSemester() {
        return Integer.parseInt(getStringCellValue(SEMESTER_NUMBER_ROW_INDEX, 2).replace(SEMESTER_NUMBER_PREFIX, StringUtils.EMPTY).trim());
    }

    private int getCourse() {
        return Integer.parseInt(getStringCellValue(6, 0).split(" ")[1]);
    }

}

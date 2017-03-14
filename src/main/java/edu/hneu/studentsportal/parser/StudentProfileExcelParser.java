package edu.hneu.studentsportal.parser;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import edu.hneu.studentsportal.entity.*;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.GroupRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.PictureData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Log4j
@Component
@Scope("prototype")
public class StudentProfileExcelParser extends AbstractExcelParser<Student> {

    private static final int LEFT_SEMESTER_COLL = 0;
    private static final int RIGHT_SEMESTER_COLL = 6;
    private static final String MASTER_PROGRAM_HOLDER = "МАГІСТЕРСЬКА ПРОГРАМА";
    private static final String SPECIALITY_HOLDER = "СПЕЦІАЛЬНІСТЬ";
    private static final String COURSER_DIRECTION_HOLDER = "НАПРЯМ ПІДГОТОВКИ";
    private static final String HEADER_HOLDER = "ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН";
    private static final String COURSER_HOLDER = "КУРС";
    private static final String INCOME_YEAR_HOLDER = "РІК НАВЧАННЯ";
    private static final String FACULTY_DIRECTOR_HOLDER = "Декан факультету";
    private static final String SEMESTER_END_HOLDER = "ВСЬОГО ЗА";

    private static final Map<String, DisciplineType> DISCIPLINE_TYPES_MAP = ImmutableMap.of(
            "МАГ-МАЙНОР", DisciplineType.MAGMAYNOR,
            "МАЙНОР", DisciplineType.MAYNOR,
            "МЕЙДЖОР", DisciplineType.MAJOR
    );

    @Resource
    private GroupRepository groupRepository;

    @Override
    public Student extractModel() {
        Indexer indexer = Indexer.of(3);
        String header = getStringCellValue(indexer.value);
        if (isFalse(header.contains(HEADER_HOLDER))) {
            log.info("File is not valid. Header :" + header);
            throw new RuntimeException("Student profile is not valid");
        }

        indexer.next();
        Student student = Student.builder()
                .surname(getString2CellValue(indexer.next()))
                .name(getString2CellValue(indexer.next()))
                .passportNumber(getString2CellValue(indexer.next()).split("\\.")[0])
                .faculty(getString2CellValue(indexer.next()))
                .incomeYear(getIntegerCellValue(indexer.next(), 2))
                .contactInfo(extractContacts(indexer))
                .speciality(getString2CellValue(indexer.next()))
                .educationProgram(extractMasterEducationProgram(indexer))
                .studentGroup(extractGroup(indexer))
                .courses(extractCourses(indexer))
                .photo(extractProfileImage())
                .build();

        return student;
    }

    private List<String> extractContacts(Indexer indexer) {
        List<String> contacts = Lists.newArrayList();
        while (indexer.value < 100 && isNotSpecialityLabel(indexer.value + 1))
            contacts.add(getString2CellValue(indexer.next()));
        return contacts;
    }

    private boolean isNotSpecialityLabel(int row) {
        String stringCellValue = getStringCellValue(row);
        return isFalse(stringCellValue.contains(COURSER_DIRECTION_HOLDER)) && isFalse(stringCellValue.contains(SPECIALITY_HOLDER));
    }

    private String extractMasterEducationProgram(Indexer indexer) {
        boolean isMasterProgram = getStringCellValue(indexer.value + 1).contains(MASTER_PROGRAM_HOLDER);
        return isMasterProgram ? getString2CellValue(indexer.next()) : null;
    }

    private Group extractGroup(Indexer indexer) {
        String groupName = getString2CellValue(indexer.next());
        return groupRepository.findByName(groupName);
    }

    private List<Course> extractCourses(Indexer indexer) {
        List<Course> courses = Lists.newArrayList();
        while (isNotFileEnd(indexer.next()))
            if (isCourseLabel(indexer))
                courses.add(extractCourse(indexer));
        return courses;
    }

    private boolean isCourseLabel(Indexer indexer) {
        String stringCellValue = getStringCellValue(indexer.value);
        return stringCellValue.contains(COURSER_HOLDER) || stringCellValue.contains(INCOME_YEAR_HOLDER);
    }

    private boolean isNotFileEnd(int row) {
        return row < 100 && isFalse(getString1CellValue(row).contains(FACULTY_DIRECTOR_HOLDER));
    }

    private Course extractCourse(Indexer indexer) {
        Course course = new Course();
        course.setLabel(getStringCellValue(indexer.value));
        course.setSemesters(extractSemesters(indexer));
        return course;
    }

    private List<Semester> extractSemesters(Indexer indexer) {
        indexer.next();
        return Lists.newArrayList(extractSemester(indexer, LEFT_SEMESTER_COLL), extractSemester(indexer, RIGHT_SEMESTER_COLL))
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Semester> extractSemester(Indexer indexer, int col) {
        Indexer internalSemesterIndex = Indexer.of(indexer.value);
        String semesterLabel = getStringCellValue(internalSemesterIndex.value, col);
        if (StringUtils.isNotBlank(semesterLabel)) {
            Semester semester = new Semester();
            semester.setLabel(semesterLabel);

            internalSemesterIndex.next();
            internalSemesterIndex.next();

            semester.setDisciplines(extractDisciplines(internalSemesterIndex, col));
            semester.setTotal(getIntegerCellValue(internalSemesterIndex.value, col + 2));
            return Optional.of(semester);
        }
        return Optional.empty();
    }

    private List<Discipline> extractDisciplines(Indexer internalSemesterIndex, int col) {
        final List<Discipline> disciplines = Lists.newArrayList();
        while (isNotSemesterEnd(internalSemesterIndex.value, col + 1)) {
            if (isNotEmpty(getStringCellValue(internalSemesterIndex.value, col + 1)))
                disciplines.add(extractDiscipline(internalSemesterIndex.value, col + 1));
            internalSemesterIndex.next();
        }
        return disciplines;
    }

    private boolean isNotSemesterEnd(int row, int col) {
        return isFalse(getStringCellValue(row, col).contains(SEMESTER_END_HOLDER));
    }

    private Discipline extractDiscipline(int row, int col) {
        Discipline discipline = new Discipline();
        String disciplineNameOrType = getStringCellValue(row, col);
        DisciplineType disciplineType = DISCIPLINE_TYPES_MAP.getOrDefault(disciplineNameOrType, DisciplineType.REGULAR);
        discipline.setType(disciplineType);
        discipline.setCredits(getStringCellValue(row, col + 1));
        discipline.setControlForm(getStringCellValue(row, col + 2));
        if (disciplineType == DisciplineType.REGULAR)
            discipline.setLabel(disciplineNameOrType);
        return discipline;
    }


    private byte[] extractProfileImage() {
        List<? extends PictureData> allPictures = workbook.getAllPictures();
        if (allPictures.isEmpty())
            return null;
        return Iterables.getLast(allPictures).getData();
    }
}

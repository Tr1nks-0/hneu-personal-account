package edu.hneu.studentsportal.parser;


import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.PictureData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import edu.hneu.studentsportal.entity.Course;
import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.Semester;
import edu.hneu.studentsportal.entity.StudentProfile;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.GroupRepository;

@Component
@Scope("prototype")
public class StudentProfileExcelParser extends AbstractExcelParser<StudentProfile> {

    private static final Logger LOG = Logger.getLogger(StudentProfileExcelParser.class);

    private static final int LEFT_SEMESTER_COLL = 0;
    private static final int RIGHT_SEMESTER_COLL = 6;

    @Resource
    private GroupRepository groupRepository;

    private Integer rowNumber;

    @Override
    public StudentProfile extractModel() {
        final String header = getStringCellValue(3, 0);
        if (!header.contains("ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН")) {
            LOG.info("File is not valid. Header :" + header);
            throw new RuntimeException("Student profile is not valid");
        }
        rowNumber = 5;
        final StudentProfile studentProfile = new StudentProfile();
        studentProfile.setSurname(getStringCellValue(rowNumber, 2));
        studentProfile.setName(getStringCellValue(++rowNumber, 2));
        studentProfile.setPassportNumber(getStringCellValue(++rowNumber, 2).split("\\.")[0]);
        studentProfile.setFaculty(getStringCellValue(++rowNumber, 2));
        studentProfile.setIncomeYear(getIntegerCellValue(++rowNumber, 2));
        studentProfile.setContactInfo(getContactInfo());
        studentProfile.setSpeciality(getStringCellValue(rowNumber, 2));
        studentProfile.setEducationProgram(getEducationProgram(++rowNumber));
        studentProfile.setStudentGroup(groupRepository.findByName(getStringCellValue(++rowNumber, 2)));
        studentProfile.setCourses(getCourses());
        studentProfile.setPhoto(getProfileImage());
        return studentProfile;
    }

    private String getEducationProgram(final Integer row) {
        final String rowValue = getStringCellValue(row, 0);
        if(rowValue.contains("МАГІСТЕРСЬКА ПРОГРАМА")) {
            return getStringCellValue(row, 1);
        }
        return null;
    }

    private List<Course> getCourses() {
        final List<Course> courses = new ArrayList<>();
        while (++rowNumber < 100) {
            if (isCourseLabel(rowNumber))
                courses.add(getCourse(rowNumber));
            if (isFileEnd(rowNumber))
                break;
        }
        return courses;
    }

    private Course getCourse(Integer rowNumber) {
        final Course course = new Course();
        course.setLabel(getStringCellValue(rowNumber, 0));
        course.setSemesters(getSemesters(++rowNumber));
        return course;
    }

    private List<Semester> getSemesters(final Integer rowNumber) {
        final Optional<Semester> semesterLeft = getSemester(rowNumber, LEFT_SEMESTER_COLL);
        final Optional<Semester> semesterRight = getSemester(rowNumber, RIGHT_SEMESTER_COLL);
        return Lists.newArrayList(semesterLeft, semesterRight).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Semester> getSemester(int rowNumber, final Integer collNumber) {
        final String semesterLabel = getStringCellValue(rowNumber, collNumber);
        if (StringUtils.isNotBlank(semesterLabel)) {
            final Semester semester = new Semester();
            semester.setLabel(semesterLabel);
            final List<Discipline> disciplines = new ArrayList<>();
            rowNumber += 2;
            while (isNotSemesterEnd(rowNumber, collNumber + 1)) {
                if (isNotEmpty(getStringCellValue(rowNumber, collNumber + 1)))
                    disciplines.add(getDiscipline(rowNumber, collNumber + 1));
                rowNumber++;
            }
            if (disciplines.isEmpty())
                throw new RuntimeException("Disciplines were not found");
            semester.setDisciplines(disciplines);
            semester.setTotal(getIntegerCellValue(rowNumber, collNumber + 2));
            return Optional.of(semester);
        }
        return Optional.empty();
    }

    private Discipline getDiscipline(final int rowNumber, final int collNumber) {
        final Discipline discipline = new Discipline();
        final String disciplineName = getStringCellValue(rowNumber, collNumber);
        if ("МАГ-МАЙНОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAGMAYNOR);
        else if ("МАЙНОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAYNOR);
        else if ("МЕЙДЖОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAJOR);
        else {
            discipline.setType(DisciplineType.REGULAR);
            discipline.setLabel(disciplineName);
        }
        discipline.setCredits(getStringCellValue(rowNumber, collNumber + 1));
        discipline.setControlForm(getStringCellValue(rowNumber, collNumber + 2));
        return discipline;
    }

    private List<String> getContactInfo() {
        final List<String> contacts = new ArrayList<>();
        while (++rowNumber < 100 && isNotSpecialityLabel(rowNumber)) {
            contacts.add(getStringCellValue(rowNumber, 2));
        }
        if (contacts.isEmpty())
            throw new RuntimeException("Contacts were not found");
        return contacts;
    }

    private boolean isNotSpecialityLabel(final Integer rowNumber) {
        final String stringCellValue = getStringCellValue(rowNumber, 0);
        return isFalse(stringCellValue.contains("НАПРЯМ ПІДГОТОВКИ"))
                && isFalse(stringCellValue.contains("СПЕЦІАЛЬНІСТЬ"));
    }

    public boolean isCourseLabel(final Integer rowNumber) {
        final String stringCellValue = getStringCellValue(rowNumber, 0);
        return stringCellValue.contains("КУРС") || stringCellValue.contains("РІК НАВЧАННЯ");
    }

    public boolean isFileEnd(final Integer rowNumber) {
        return getStringCellValue(rowNumber, 1).contains("Декан факультету");
    }

    public boolean isNotSemesterEnd(final Integer semesterRow, final int cellrowNumber) {
        return isFalse(getStringCellValue(semesterRow, cellrowNumber).contains("ВСЬОГО ЗА"));
    }

    private byte[] getProfileImage() {
        final List<? extends PictureData> allPictures = workbook.getAllPictures();
        if (allPictures.isEmpty())
            return null;
        return Iterables.getLast(allPictures).getData();
    }
}

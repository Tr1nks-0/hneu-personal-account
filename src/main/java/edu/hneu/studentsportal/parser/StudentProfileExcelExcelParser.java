package edu.hneu.studentsportal.parser;


import edu.hneu.studentsportal.model.Course;
import edu.hneu.studentsportal.model.Discipline;
import edu.hneu.studentsportal.model.Semester;
import edu.hneu.studentsportal.model.StudentProfile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class StudentProfileExcelExcelParser extends AbstractExcelParser<StudentProfile> {

    private static final int LEFT_SEMESTER_COLL = 0;
    private static final int RIGHT_SEMESTER_COLL = 6;

    @Override
    public StudentProfile extractModel() {
        String header = getStringCellValue(3, 0);
        if (!header.contains("ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН")) {
            throw new RuntimeException("Student profile is not valid");
        }
        currentIndex = 5;
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setSurname(getStringCellValue(currentIndex++, 2));
        studentProfile.setName(getStringCellValue(currentIndex++, 2));
        studentProfile.setFaculty(getStringCellValue(currentIndex++, 2));
        studentProfile.setIncomeYear(getIntegerCellValue(currentIndex++, 2));
        studentProfile.setContactInfo(getContactInfo(currentIndex));
        studentProfile.setSpeciality(getStringCellValue(currentIndex++, 2));
        studentProfile.setGroup(getStringCellValue(currentIndex++, 2));
        studentProfile.setCourses(getCourses(currentIndex));
        studentProfile.setId(getId(studentProfile));
        return studentProfile;
    }

    private String getId(StudentProfile studentProfile) {
        String id = studentProfile.getSurname() + studentProfile.getName() + studentProfile.getGroup();
        return id.replace(" ", "").toLowerCase().trim();
    }

    private List<Course> getCourses(Integer currentIndex) {
        List<Course> courses = new ArrayList<>();
        while (currentIndex++ < 100) {
            if (isCourseLabel())
                courses.add(getCourse());
            if (isFileEnd())
                break;
        }
        return courses;
    }

    private Course getCourse() {
        Course course = new Course();
        course.setLabel(getStringCellValue(currentIndex++, 0));
        course.setSemesters(getSemesters());
        return course;
    }

    private ArrayList<Semester> getSemesters() {
        Semester semesterLeft = getSemester(currentIndex, LEFT_SEMESTER_COLL);
        Semester semesterRight = getSemester(currentIndex, RIGHT_SEMESTER_COLL);
        return new ArrayList<>(asList(semesterLeft, semesterRight));
    }

    private Semester getSemester(int rowNumber, int collNumber) {
        Semester semester = new Semester();
        semester.setLabel(getStringCellValue(rowNumber, collNumber));
        List<Discipline> disciplines = new ArrayList<>();
        while (isNotSemesterEnd(rowNumber, collNumber + 1)) {
            if (isNotEmpty(getStringCellValue(rowNumber, 1)))
                disciplines.add(getDiscipline(rowNumber));
            rowNumber++;
        }
        if (disciplines.isEmpty())
            throw new RuntimeException("Disciplines were not found");
        semester.setDisciplines(disciplines);
        semester.setTotal(getIntegerCellValue(rowNumber, collNumber + 2));
        return semester;
    }

    private Discipline getDiscipline(int rowNumber) {
        Discipline discipline = new Discipline();
        discipline.setLabel(getStringCellValue(rowNumber, 1));
        discipline.setCredits(getStringCellValue(rowNumber, 2));
        discipline.setControlForm(getStringCellValue(rowNumber, 3));
        return discipline;
    }

    private List<String> getContactInfo(int currentIndex) {
        List<String> contacts = new ArrayList<>();
        while (currentIndex++ < 100 && isNotSpecialityLabel(currentIndex))
            contacts.add(getStringCellValue(currentIndex, 2));
        if (contacts.isEmpty())
            throw new RuntimeException("Contacts were not found");
        return contacts;
    }

    private boolean isNotSpecialityLabel(int currentIndex) {
        return isFalse(getStringCellValue(currentIndex, 0).contains("НАПРЯМ ПІДГОТОВКИ"));
    }

    public boolean isCourseLabel() {
        return getStringCellValue(currentIndex, 0).contains("КУРС");
    }

    public boolean isFileEnd() {
        return getStringCellValue(currentIndex, 1).contains("Декан факультету");
    }

    public boolean isNotSemesterEnd(int semesterRow, int cellIndex) {
        return isFalse(getStringCellValue(semesterRow, cellIndex).contains("ВСЬОГО ЗА"));
    }
}

package edu.hneu.studentsportal.parser;


import static java.util.Arrays.asList;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.PictureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.model.Course;
import edu.hneu.studentsportal.model.Discipline;
import edu.hneu.studentsportal.model.Semester;
import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.model.type.DisciplineType;
import edu.hneu.studentsportal.service.FileService;

@Component
@Scope("prototype")
public class StudentProfileExcelParser extends AbstractExcelParser<StudentProfile> {

    private static final Logger LOG = Logger.getLogger(StudentProfileExcelParser.class);

    private static final int LEFT_SEMESTER_COLL = 0;
    private static final int RIGHT_SEMESTER_COLL = 6;

    @Autowired
    private FileService fileService;

    @Value("${profile.photo.location}")
    public String profilePhotoLocation;

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
        studentProfile.setGroup(getStringCellValue(++rowNumber, 2));
        studentProfile.setCourses(getCourses());
        studentProfile.setId(getId(studentProfile));
        studentProfile.setProfileImage(getProfileImage(studentProfile));
        return studentProfile;
    }

    private String getId(final StudentProfile studentProfile) {
        final String id = studentProfile.getSurname() + studentProfile.getName() + studentProfile.getGroup();
        return id.replace(" ", "").toLowerCase().trim();
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

    private ArrayList<Semester> getSemesters(final Integer rowNumber) {
        final Semester semesterLeft = getSemester(rowNumber, LEFT_SEMESTER_COLL);
        final Semester semesterRight = getSemester(rowNumber, RIGHT_SEMESTER_COLL);
        return new ArrayList<>(asList(semesterLeft, semesterRight));
    }

    private Semester getSemester(int rowNumber, final Integer collNumber) {
        final Semester semester = new Semester();
        semester.setLabel(getStringCellValue(rowNumber, collNumber));
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
        return semester;
    }

    private Discipline getDiscipline(final int rowNumber, final int collNumber) {
        final Discipline discipline = new Discipline();
        final String disciplineName = getStringCellValue(rowNumber, collNumber);
        if("МАГ-МАЙНОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAGMAYNOR);
        else if("МАЙНОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAYNOR);
        else if("МЕЙДЖОР".equals(disciplineName))
            discipline.setType(DisciplineType.MAJOR);
        else {
            discipline.setType(DisciplineType.REGULAR);
            discipline.setLabel(disciplineName);
        }
        discipline.setCredits(getStringCellValue(rowNumber, collNumber + 1));
        discipline.setControlForm(getStringCellValue(rowNumber, collNumber + 2));
        discipline.setMarkPosition(new Pair<>(rowNumber, collNumber + 3));
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
        return isFalse(getStringCellValue(rowNumber, 0).contains("НАПРЯМ ПІДГОТОВКИ"));
    }

    public boolean isCourseLabel(final Integer rowNumber) {
        return getStringCellValue(rowNumber, 0).contains("КУРС");
    }

    public boolean isFileEnd(final Integer rowNumber) {
        return getStringCellValue(rowNumber, 1).contains("Декан факультету");
    }

    public boolean isNotSemesterEnd(final Integer semesterRow, final int cellrowNumber) {
        return isFalse(getStringCellValue(semesterRow, cellrowNumber).contains("ВСЬОГО ЗА"));
    }

    private String getProfileImage(final StudentProfile studentProfile) {
        final List<? extends PictureData> allPictures = workbook.getAllPictures();
        if(allPictures.isEmpty())
            return null;
        try {
            final PictureData profilePhoto = allPictures.get(allPictures.size() - 1);
            final String profilePhotoFileName = studentProfile.getId() + "/" + "photo." + profilePhoto.suggestFileExtension();
            final String classPath = this.getClass().getResource("/").getPath();
            final String filePath = classPath + profilePhotoLocation + "/" +profilePhotoFileName;
            final File file = new File(filePath);
            fileService.createDirectoryIfNotExist(file.getParentFile());
            final FileOutputStream out = new FileOutputStream(file);
            out.write(profilePhoto.getData());
            out.close();
            return profilePhotoFileName;
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

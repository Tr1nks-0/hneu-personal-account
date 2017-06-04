package edu.hneu.studentsportal.parser.impl;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.*;
import edu.hneu.studentsportal.service.MessageService;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.PictureData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
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
    private static final String COURSER_HOLDER = "КУРС";
    private static final String INCOME_YEAR_HOLDER = "РІК НАВЧАННЯ";
    private static final String FACULTY_DIRECTOR_HOLDER = "Декан факультету";
    private static final String SEMESTER_END_HOLDER = "ВСЬОГО ЗА";
    private static final String VALID_HEADER_HOLDER = "ІНДИВІДУАЛЬНИЙ НАВЧАЛЬНИЙ ПЛАН";

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private MessageService messageService;

    @Override
    public Student extractModel() {
        Indexer indexer = Indexer.of(3);
        if (isFalse(getStringCellValue(indexer.getValue()).contains(VALID_HEADER_HOLDER)))
            throw new IllegalArgumentException(messageService.invalidStudentError());
        indexer.next();
        Student student = new Student();
        student.setSurname(getString2CellValue(indexer.next()));
        student.setName(getString2CellValue(indexer.next()));
        student.setPassportNumber(getString2CellValue(indexer.next()).split("\\.")[0]);
        student.setFaculty(extractFaculty(indexer));
        student.setIncomeYear(getIntegerCellValue(indexer.next(), 2));
        student.setContactInfo(extractContacts(indexer));
        student.setSpeciality(extractSpeciality(indexer, student.getFaculty()));
        student.setEducationProgram(extractMasterEducationProgram(indexer));
        student.setGroup(extractGroup(indexer));
        student.setDisciplineMarks(extractDisciplinesInternal(student.getSpeciality(), student.getEducationProgram(), indexer));
        student.setPhoto(extractProfileImage());
        return student;
    }

    private Faculty extractFaculty(Indexer indexer) {
        String facultyName = getString2CellValue(indexer.next());
        return facultyRepository.findByName(facultyName).orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentFacultyError()));
    }

    private List<String> extractContacts(Indexer indexer) {
        List<String> contacts = Lists.newArrayList();
        while (indexer.getValue() < 100 && isNotSpecialityLabel(indexer.getValue() + 1))
            contacts.add(getString2CellValue(indexer.next()));
        return contacts;
    }

    private Speciality extractSpeciality(Indexer indexer, Faculty faculty) {
        String specialityName = getString2CellValue(indexer.next());
        return specialityRepository.findByNameAndFaculty(specialityName, faculty)
                .orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentSpecialityError()));
    }

    private EducationProgram extractMasterEducationProgram(Indexer indexer) {
        boolean isMasterProgram = getStringCellValue(indexer.getValue() + 1).contains(MASTER_PROGRAM_HOLDER);
        if (isMasterProgram) {
            String educationProgramName = getString2CellValue(indexer.next());
            return educationProgramRepository.findByName(educationProgramName)
                    .orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentEducationProgramError()));
        }
        return null;
    }

    private Group extractGroup(Indexer indexer) {
        String groupName = getString2CellValue(indexer.next());
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentGroupError(groupName)));
    }

    private List<DisciplineMark> extractDisciplinesInternal(Speciality speciality, EducationProgram educationProgram, Indexer indexer) {
        List<DisciplineMark> allDisciplines = Lists.newArrayList();
        int course = 1;
        while (isNotFileEnd(indexer.next())) {
            if (isCourseLabel(indexer)) {
                indexer.next();
                indexer.next();

                List<DisciplineMark> disciplinesPerCourse = extractSemesters(indexer, course).stream()
                        .map(populateDisciplineSpeciality(speciality))
                        .map(populateDisciplineEducationProgram(educationProgram))
                        .map(findDisciplineByExample())
                        .map(DisciplineMark::new)
                        .collect(Collectors.toList());
                allDisciplines.addAll(disciplinesPerCourse);
                course++;
            }
        }
        return allDisciplines;
    }

    private Function<Discipline, Discipline> populateDisciplineSpeciality(Speciality speciality) {
        return discipline -> {
            discipline.setSpeciality(speciality);
            return discipline;
        };
    }

    private Function<Discipline, Discipline> populateDisciplineEducationProgram(EducationProgram educationProgram) {
        return discipline -> {
            discipline.setEducationProgram(educationProgram);
            return discipline;
        };
    }

    private Function<Discipline, Discipline> findDisciplineByExample() {
        return discipline -> disciplineRepository.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgramAndCreditsAndControlForm(
                discipline.getLabel(), discipline.getCourse(), discipline.getSemester(),
                discipline.getSpeciality(), discipline.getEducationProgram(),
                discipline.getCredits(), discipline.getControlForm())
                    .orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentDisciplineError(discipline.getLabel())));
    }

    private boolean isNotFileEnd(int row) {
        return row < 100 && isFalse(getString1CellValue(row).contains(FACULTY_DIRECTOR_HOLDER));
    }

    private boolean isCourseLabel(Indexer indexer) {
        String stringCellValue = getStringCellValue(indexer.getValue());
        return stringCellValue.contains(COURSER_HOLDER) || stringCellValue.contains(INCOME_YEAR_HOLDER);
    }

    private List<Discipline> extractSemesters(Indexer indexer, int course) {
        return Lists.newArrayList(extractSemester(indexer, LEFT_SEMESTER_COLL, course, 1), extractSemester(indexer, RIGHT_SEMESTER_COLL, course, 2))
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Discipline> extractSemester(Indexer indexer, int rightSemesterColl, int course, int semester) {
        Indexer internalSemesterIndex = Indexer.of(indexer.getValue());
        final List<Discipline> disciplines = Lists.newArrayList();
        if (isNotEmpty(getStringCellValue(internalSemesterIndex.getValue() + 1, rightSemesterColl + 1))) {
            while (isNotSemesterEnd(internalSemesterIndex.getValue(), rightSemesterColl + 1)) {
                String disciplineLabel = getStringCellValue(internalSemesterIndex.getValue(), rightSemesterColl + 1);
                if (isNotEmpty(disciplineLabel))
                    disciplines.add(extractDiscipline(internalSemesterIndex.getValue(), rightSemesterColl + 1, disciplineLabel, course, semester));
                internalSemesterIndex.next();
            }
        }
        return disciplines;
    }

    private boolean isNotSemesterEnd(int row, int col) {
        return isFalse(getStringCellValue(row, col).contains(SEMESTER_END_HOLDER));
    }

    private boolean isNotSpecialityLabel(int row) {
        String stringCellValue = getStringCellValue(row);
        return isFalse(stringCellValue.contains(COURSER_DIRECTION_HOLDER)) && isFalse(stringCellValue.contains(SPECIALITY_HOLDER));
    }

    private Discipline extractDiscipline(int row, int col, String label, int course, int semester) {
        Discipline discipline = new Discipline();
        Indexer columnIndex = Indexer.of(col);
        discipline.setLabel(label);
        discipline.setCredits(getIntegerCellValue(row, columnIndex.next()));
        discipline.setControlForm(DisciplineFormControl.of(getStringCellValue(row, columnIndex.next()).trim()));
        discipline.setCourse(course);
        discipline.setSemester(semester);
        return discipline;
    }

    private byte[] extractProfileImage() {
        List<? extends PictureData> allPictures = workbook.getAllPictures();
        if (allPictures.isEmpty())
            throw new IllegalArgumentException(messageService.invalidStudentPhotoError());
        return Iterables.getLast(allPictures).getData();
    }
}

package edu.hneu.studentsportal.parser.impl;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.DisciplineDTO;
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
    private static final String EDUCATION_PROGRAM_HOLDER = "спеціалізація";
    private static final String SPECIALITY_HOLDER = "спеціальність";
    private static final String COURSER_DIRECTION_HOLDER = "напрям підготовки";
    private static final String COURSER_HOLDER = "курс";
    private static final String INCOME_YEAR_HOLDER = "рік навчання";
    private static final String FACULTY_DIRECTOR_HOLDER = "декан факультету";
    private static final String SEMESTER_END_HOLDER = "всього за";
    private static final String VALID_HEADER_HOLDER = "індивідуальний навчальний план";

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
        if (isFalse(getStringCellValue(indexer.getValue()).toLowerCase().contains(VALID_HEADER_HOLDER)))
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
        student.setEducationProgram(extractEducationProgram(indexer));
        student.setGroup(extractGroup(indexer));

        DisciplineDTO disciplineDTO = new DisciplineDTO(student.getSpeciality(), student.getEducationProgram());
        student.setDisciplineMarks(extractDisciplinesInternal(indexer, disciplineDTO));

        student.setPhoto(extractProfileImage());
        return student;
    }

    private Faculty extractFaculty(Indexer indexer) {
        String facultyName = getString2CellValue(indexer.next());
        Optional<Faculty> faculty = facultyRepository.findByName(facultyName);
        return faculty.orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentFacultyError()));
    }

    private List<String> extractContacts(Indexer indexer) {
        List<String> contacts = Lists.newArrayList();
        while (indexer.getValue() < 100 && isNotSpecialityLabel(indexer.getValue() + 1))
            contacts.add(getString2CellValue(indexer.next()));
        return contacts;
    }

    private Speciality extractSpeciality(Indexer indexer, Faculty faculty) {
        String specialityName = getString2CellValue(indexer.next());
        Optional<Speciality> speciality = specialityRepository.findByNameAndFaculty(specialityName, faculty);
        return speciality.orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentSpecialityError()));
    }

    private EducationProgram extractEducationProgram(Indexer indexer) {
        boolean hasEducationProgram = getStringCellValue(indexer.getValue() + 1).toLowerCase().contains(EDUCATION_PROGRAM_HOLDER);
        if (hasEducationProgram) {
            String educationProgramName = getString2CellValue(indexer.next());
            Optional<EducationProgram> educationProgram = educationProgramRepository.findByName(educationProgramName);
            return educationProgram.orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentEducationProgramError()));
        }
        return null;
    }

    private Group extractGroup(Indexer indexer) {
        String groupName = getString2CellValue(indexer.next());
        Optional<Group> group = groupRepository.findByName(groupName);
        return group.orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentGroupError(groupName)));
    }

    private List<DisciplineMark> extractDisciplinesInternal(Indexer indexer, DisciplineDTO disciplineDTO) {
        List<DisciplineMark> allDisciplines = Lists.newArrayList();
        int course = 1;
        while (isNotFileEnd(indexer.next())) {
            if (isCourseLabel(indexer)) {
                indexer.next2();
                disciplineDTO.setCourse(course);
                extractSemesterDisciplines(indexer, disciplineDTO).stream().map(DisciplineMark::new).forEach(allDisciplines::add);
                course++;
            }
        }
        return allDisciplines;
    }

    private boolean isNotFileEnd(int row) {
        return row < 100 && isFalse(getString1CellValue(row).toLowerCase().contains(FACULTY_DIRECTOR_HOLDER));
    }

    private boolean isCourseLabel(Indexer indexer) {
        String stringCellValue = getStringCellValue(indexer.getValue()).toLowerCase();
        return stringCellValue.contains(COURSER_HOLDER) || stringCellValue.contains(INCOME_YEAR_HOLDER);
    }

    private List<Discipline> extractSemesterDisciplines(Indexer indexer, DisciplineDTO disciplineDTO) {
        Speciality speciality = disciplineDTO.getSpeciality();
        EducationProgram educationProgram = disciplineDTO.getEducationProgram();
        int course = disciplineDTO.getCourse();
        DisciplineDTO disciplineDTO1 = new DisciplineDTO(speciality, educationProgram, course, 1);
        DisciplineDTO disciplineDTO2 = new DisciplineDTO(speciality, educationProgram, course, 2);
        List<Discipline> disciplinesFooSemester1 = extractSemester(indexer, LEFT_SEMESTER_COLL, disciplineDTO1);
        List<Discipline> disciplinesFooSemester2 = extractSemester(indexer, RIGHT_SEMESTER_COLL, disciplineDTO2);
        return Lists.newArrayList(disciplinesFooSemester1, disciplinesFooSemester2).stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<Discipline> extractSemester(Indexer rowIndexer, int col, DisciplineDTO disciplineDTO) {
        Indexer internalSemesterIndex = Indexer.of(rowIndexer.getValue());
        List<Discipline> disciplines = Lists.newArrayList();
        if (isNotEmpty(getStringCellValue(internalSemesterIndex.getValue() + 1, col + 1))) {
            while (isNotSemesterEnd(internalSemesterIndex.getValue(), col + 1)) {
                String disciplineLabel = getStringCellValue(internalSemesterIndex.getValue(), col + 1);
                if (isNotEmpty(disciplineLabel)) {
                    disciplineDTO.setLabel(disciplineLabel);
                    disciplines.add(findDiscipline(disciplineDTO));
                }
                internalSemesterIndex.next();
            }
        }
        return disciplines;
    }

    private boolean isNotSemesterEnd(int row, int col) {
        return isFalse(getStringCellValue(row, col).toLowerCase().contains(SEMESTER_END_HOLDER));
    }

    private boolean isNotSpecialityLabel(int row) {
        String stringCellValue = getStringCellValue(row).toLowerCase();
        return isFalse(stringCellValue.contains(COURSER_DIRECTION_HOLDER)) && isFalse(stringCellValue.contains(SPECIALITY_HOLDER));
    }

    private Discipline findDiscipline(DisciplineDTO disciplineDTO) {
        Optional<Discipline> discipline = disciplineRepository.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(
                disciplineDTO.getLabel(),
                disciplineDTO.getCourse(),
                disciplineDTO.getSemester(),
                disciplineDTO.getSpeciality(),
                disciplineDTO.getEducationProgram());
        return discipline.orElseThrow(() -> new IllegalArgumentException(messageService.invalidStudentDisciplineError(disciplineDTO.getLabel())));
    }

    private byte[] extractProfileImage() {
        List<? extends PictureData> allPictures = workbook.getAllPictures();
        if (allPictures.isEmpty())
            throw new IllegalArgumentException(messageService.invalidStudentPhotoError());
        return Iterables.getLast(allPictures).getData();
    }
}

package edu.hneu.studentsportal.parser.impl;


import com.google.common.collect.Lists;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.StudentService;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Log4j
@Component
@Scope("prototype")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentsExcelParser extends AbstractExcelParser<Map<String, Student>> {

    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final DisciplineMarkService disciplineMarkService;
    private final FileService fileService;

    @Override
    public Map<String, Student> extractModel() {
        Map<String, Student> students = new HashMap<>();
        Indexer indexer = Indexer.of(0);
        validateDisciplinesExcelFile(indexer.getValue());
        while (isNotFileEnd(indexer.next())) {
            String studentKey = getStudentKey(indexer);
            Try.of(() -> parseStudent(indexer))
                    .andThen(student -> students.put(studentKey, student))
                    .onFailure(e -> {
                        students.put(studentKey, null);
                        log.warn(e.getMessage(), e);
                    });
        }
        return students;
    }

    private String getStudentKey(Indexer indexer) {
        return getString1CellValue(indexer).trim()
                + " - " + getStringCellValue(indexer, 3).trim()
                + " " + getStringCellValue(indexer, 4).trim();
    }

    private void validateDisciplinesExcelFile(int row) {
        validateHeaders(row, newArrayList("Фото", "Група", "Рік вступу", "Прізвище", "Ім’я, по батькові",
                "Серія паспорту", "ID у сервісі розкладу студентів", "Контактна інформація (дані через кому, без пробілів)", "Контракт"));
    }

    private boolean isNotFileEnd(int row) {
        return isFalse(isEmpty(getString1CellValue(row)));
    }

    private Student parseStudent(Indexer indexer) {
        Group group = parseGroup(indexer);
        Speciality speciality = group.getSpeciality();
        EducationProgram educationProgram = group.getEducationProgram();

        String surname = getStringCellValue(indexer, 3).trim();
        String name = getStringCellValue(indexer, 4).trim();
        String email = studentService.receiveStudentEmail(name, surname, group.getName());

        List<DisciplineMark> marks = disciplineMarkService.createMarksForNewStudent(speciality, educationProgram);

        Student student = Student.builder()
                .group(group)
                .incomeYear(getIntegerCellValue(indexer, 2))
                .name(name)
                .surname(surname)
                .passportNumber(getStringCellValue(indexer, 5).trim())
                .scheduleStudentId(Long.valueOf(getIntegerCellValue(indexer, 6)))
                .contactInfo(Lists.newArrayList(getStringCellValue(indexer, 7).trim().split(",")))
                .email(email)
                .faculty(speciality.getFaculty())
                .speciality(speciality)
                .educationProgram(educationProgram)
                .photo(fileService.getProfilePhoto(getStudentPhoto(indexer)))
                .disciplineMarks(marks)
                .contract("+".equals(getStringCellValue(indexer, 8)))
                .build();
        marks.forEach(mark -> mark.setStudent(student));
        return student;
    }

    private byte[] getStudentPhoto(Indexer indexer) {
        List<? extends PictureData> allPictures = workbook.getAllPictures();
        Optional<XSSFPictureData> profilePhoto = allPictures.stream()
                .filter(XSSFPictureData.class::isInstance)
                .map(XSSFPictureData.class::cast)
                .filter(picture -> picture.getPackagePart().getPartName().getName().endsWith("image" + indexer.getValue() + ".png"))
                .findFirst();
        return profilePhoto.map(XSSFPictureData::getData)
                .orElseThrow(() -> new IllegalStateException("Image not found for i=" + indexer.getValue()));
    }

    private Group parseGroup(Indexer indexer) {
        String groupName = getString1CellValue(indexer).trim();
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalStateException("Cannot find group for name: " + groupName));
    }

}

package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.StudentDTO;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.hasEducationProgram;
import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.hasSpeciality;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

    private final MessageService messageService;
    private final RestOperations restTemplate;
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final DisciplineRepository disciplineRepository;
    private final FileService fileService;

    @Value("${integration.service.emails.url}")
    public String emailsIntegrationServiceUrl;

    public Student createStudent(StudentDTO studentDTO) {
        String studentEmail = receiveStudentEmail(studentDTO.getName(), studentDTO.getSurname(), studentDTO.getGroup().getName());
        List<Discipline> disciplines = disciplineRepository.findAll(where(hasSpeciality(studentDTO.getSpeciality()))
                .and(hasEducationProgram(studentDTO.getEducationProgram())));
        List<DisciplineMark> marks = disciplines.stream().map(DisciplineMark::new).collect(toList());

        final Student student = Student.builder()
                .scheduleStudentId(studentDTO.getScheduleStudentId())
                .email(studentEmail)
                .name(studentDTO.getName().trim())
                .surname(studentDTO.getSurname().trim())
                .passportNumber(studentDTO.getPassportNumber().trim())
                .faculty(studentDTO.getFaculty())
                .speciality(studentDTO.getSpeciality())
                .educationProgram(studentDTO.getEducationProgram())
                .incomeYear(studentDTO.getIncomeYear())
                .photo(fileService.getProfilePhoto(studentDTO.getPhoto()))
                .group(studentDTO.getGroup())
                .contactInfo(studentDTO.getContactInfo())
                .disciplineMarks(marks)
                .build();

        marks.forEach(m -> m.setStudent(student));
        userService.create(studentEmail, UserRole.STUDENT);
        Student createdStudent = studentRepository.save(student);
        log.info(String.format("New [%s] has been added", createdStudent));
        return createdStudent;
    }

    public String receiveStudentEmail(String name, String surname, String groupName) {
        String formattedName = name.toLowerCase().split(" ")[0];
        String formatterSurname = surname.toLowerCase().trim();
        String url = format("%s/EmailToOutController?name=%s&surname=%s&groupId=%s", emailsIntegrationServiceUrl, formattedName, formatterSurname, groupName);
        Try<String> email = Try.of(() -> restTemplate.getForEntity(url, String.class))
                .map(ResponseEntity::getBody)
                .map(String::toLowerCase);
        if (email.isSuccess() && email.get().contains("@")) {
            return email.get();
        } else {
            throw new IllegalArgumentException(messageService.emailNotFoundForStudent(formattedName + " " + formatterSurname));
        }
    }
}

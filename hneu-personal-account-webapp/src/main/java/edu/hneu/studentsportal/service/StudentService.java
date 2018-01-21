package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.StudentDTO;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static edu.hneu.studentsportal.repository.StudentRepository.StudentSpecifications.hasSpecialityAndIncomeYear;
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
    private final FileService fileService;
    private final DisciplineMarkService disciplineMarkService;

    @Value("${integration.service.emails.url}")
    public String emailsIntegrationServiceUrl;

    @SneakyThrows
    public Student createStudent(StudentDTO studentDTO) {
        String studentEmail = receiveStudentEmail(studentDTO.getName(), studentDTO.getSurname(), studentDTO.getGroup().getName());
        List<DisciplineMark> marks = disciplineMarkService.createMarksForNewStudent(studentDTO.getSpeciality(), studentDTO.getEducationProgram());

        final Student student = Student.builder()
                .scheduleStudentId(studentDTO.getScheduleStudentId())
                .email(studentEmail)
                .name(studentDTO.getName())
                .surname(studentDTO.getSurname())
                .passportNumber(studentDTO.getPassportNumber())
                .faculty(studentDTO.getFaculty())
                .speciality(studentDTO.getSpeciality())
                .educationProgram(studentDTO.getEducationProgram())
                .incomeYear(studentDTO.getIncomeYear())
                .photo(fileService.getProfilePhoto(studentDTO.getPhoto().getBytes()))
                .group(studentDTO.getGroup())
                .contactInfo(studentDTO.getContactInfo())
                .disciplineMarks(marks)
                .contract(studentDTO.isContract())
                .build();

        marks.forEach(mark -> mark.setStudent(student));
        userService.create(studentEmail, UserRole.STUDENT);
        Student createdStudent = save(student);
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

    public Student save(Student student) {
        student.setAverageMark(calculateAverageMark(student.getDisciplineMarks()));
        return studentRepository.save(student);
    }

    public Double calculateAverageMark(List<DisciplineMark> disciplineMarks) {
        List<Double> studentMarks = disciplineMarks.stream()
                .map(DisciplineMark::getMark)
                .filter(NumberUtils::isNumber)
                .map(Double::valueOf)
                .collect(toList());
        if (!studentMarks.isEmpty()) {
            double total = studentMarks.stream().mapToDouble(m -> m).sum();
            return Precision.round(total / studentMarks.size(), 2);
        }
        return null;
    }

    public Integer getStudentRating(Student student) {
        Specifications<Student> specifications = where(hasSpecialityAndIncomeYear(student.getSpeciality(), student.getIncomeYear()));
        List<Student> students = studentRepository.findAll(specifications, new Sort(Sort.Direction.DESC, "averageMark"));
        return students.indexOf(student) + 1;
    }
}

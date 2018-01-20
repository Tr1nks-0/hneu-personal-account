package edu.hneu.studentsportal.controller.management.pdf;

import com.itextpdf.text.DocumentException;
import edu.hneu.studentsportal.controller.management.AbstractManagementController;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.PdfGenerationService;
import edu.hneu.studentsportal.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.*;
import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenerateSemesterStudentsMarksPdfReportController extends AbstractManagementController implements PdfGenerationController {

    private final TemplateEngine templateEngine;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentService studentService;
    private final PdfGenerationService pdfGenerationService;

    @GetMapping("/{groupId}/disciplines/marks/pdf")
    public void getDisciplines(@PathVariable Long groupId,
                               @RequestParam(defaultValue = "1") Integer course,
                               @RequestParam(defaultValue = "1") Integer semester,
                               HttpServletResponse response) throws IOException, DocumentException {
        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        List<Student> students = studentRepository.findByGroup(group, new Sort(Sort.Direction.ASC, "surname", "name"));
        List<Discipline> disciplines = getDisciplines(course, semester, group);

        Map<String, Object> variables = new HashedMap<>();
        variables.put("faculty", group.getSpeciality().getFaculty().getName());
        variables.put("speciality", group.getSpeciality().getName());
        variables.put("educationProgram", group.getEducationProgram().getName());
        variables.put("group", group.getName());
        variables.put("course", String.valueOf(course));
        variables.put("semester", String.valueOf(semester));
        variables.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("students", students);
        variables.put("disciplines", disciplines);
        variables.put("year", getEducationYear(course, students));
        variables.put("studentsColumns", getStudentsColumns(students, disciplines));

        writePdfToResponse(response, outputStream ->
                pdfGenerationService.write(variables, "pdf/student-marks-per-semester", outputStream));
    }

    private List<Discipline> getDisciplines(int course, int semester, Group group) {
        Specifications<Discipline> spec = where(hasCourseAndSemester(course, semester))
                .and(hasSpeciality(group.getSpeciality()))
                .and(hasEducationProgram(group.getEducationProgram()))
                .and(isNotTemporal());
        return disciplineRepository.findAll(spec);
    }

    private Map<String, Map<String, String>> getStudentsColumns(List<Student> students, List<Discipline> disciplines) {
        Map<String, Map<String, String>> studentsColumns = new HashMap<>();
        students.forEach(student -> {
            Map<String, String> studentColumns = student.getDisciplineMarks().stream()
                    .filter(m -> disciplines.contains(m.getDiscipline()))
                    .collect(Collectors.toMap(m -> m.getDiscipline().getCode(), m -> nonNull(m.getMark()) ? m.getMark() : ""));
            studentColumns.put("average", Optional.ofNullable(studentService.calculateAverageMark(student)).map(String::valueOf).orElse(""));
            studentsColumns.put(student.getEmail(), studentColumns);
        });
        return studentsColumns;
    }

    private String getEducationYear(@RequestParam(defaultValue = "1") Integer course, List<Student> students) {
        int year = students.get(0).getIncomeYear() + course;
        return (year - 1) + "/" + year;
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
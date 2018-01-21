package edu.hneu.studentsportal.controller.management.pdf;

import com.google.common.collect.Lists;
import edu.hneu.studentsportal.controller.management.AbstractManagementController;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_STUDENTS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_STUDENTS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenerateStudentProfilePdfController extends AbstractManagementController implements PdfGenerationController {

    private final StudentRepository studentRepository;
    private final PdfGenerationService pdfGenerationService;
    private final DisciplineMarkService disciplineMarkService;

    @GetMapping("/{id}/pdf")
    public void getDisciplines(@PathVariable long id, HttpServletResponse response) throws IOException {
        Student student = studentRepository.getOne(id);
        Map<String, Object> variables = new HashedMap<>();
        variables.put("student", student);
        variables.put("courses", disciplineMarkService.getCourses(student));
        variables.put("studentMarks", getDisciplineMarksPerCourseAndSemester(student));
        variables.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("semestersLabels", Lists.newArrayList("I", "II", "III", "IV", "V", "VI", "VII", "VIII"));
        variables.put("semesterCredits", calculateCreditsPerSemester(student));
        variables.put("studentPhoto", new BASE64Encoder().encode(student.getPhoto()));
        writePdfToResponse(response, outputStream ->
                pdfGenerationService.write(variables, "pdf/student-profile", outputStream));
    }

    private Map<Integer, Integer> calculateCreditsPerSemester(Student student) {
        Map<Integer, Integer> creditsPerSemester = new HashMap<>();
        student.getDisciplineMarks().stream()
                .collect(Collectors.groupingBy(m -> (m.getDiscipline().getCourse() - 1) * 2 + m.getDiscipline().getSemester()))
                .forEach((semester, marks) -> creditsPerSemester.put(semester, marks.stream().mapToInt(mark -> mark.getDiscipline().getCredits()).sum()));
        return creditsPerSemester;
    }

    private Map<Integer, Map<Integer, List<DisciplineMark>>> getDisciplineMarksPerCourseAndSemester(Student student) {
        return student.getDisciplineMarks().stream().collect(
                Collectors.groupingBy(m -> m.getDiscipline().getCourse(),
                        Collectors.groupingBy(m -> m.getDiscipline().getSemester())));
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
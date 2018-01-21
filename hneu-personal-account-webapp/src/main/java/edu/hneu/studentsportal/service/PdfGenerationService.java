package edu.hneu.studentsportal.service;

import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.BaseFont;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.hneu.studentsportal.repository.DisciplineRepository.DisciplineSpecifications.*;
import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfGenerationService {

    private final TemplateEngine templateEngine;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentService studentService;
    private final DisciplineMarkService disciplineMarkService;

    public Map<String, Object> prepareStudentMarksReportPerSemester(long groupId, int course, int semester) {
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
        return variables;
    }

    private List<Discipline> getDisciplines(int course, int semester, Group group) {
        Specifications<Discipline> spec = where(hasCourseAndSemester(course, semester))
                .and(hasSpeciality(group.getSpeciality()))
                .and(hasEducationProgram(group.getEducationProgram()))
                .and(isNotTemporal());
        return disciplineRepository.findAll(spec, new Sort(Sort.Direction.ASC, "controlForm"));
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

    public Map<String, Object> prepareStudentProfile(long studentId) {
        Student student = studentRepository.getOne(studentId);
        Map<String, Object> variables = new HashedMap<>();
        variables.put("student", student);
        variables.put("courses", disciplineMarkService.getCourses(student));
        variables.put("studentMarks", getDisciplineMarksPerCourseAndSemester(student));
        variables.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        variables.put("semestersLabels", Lists.newArrayList("I", "II", "III", "IV", "V", "VI", "VII", "VIII"));
        variables.put("semesterCredits", calculateCreditsPerSemester(student));
        variables.put("studentPhoto", new BASE64Encoder().encode(student.getPhoto()));
        return variables;
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

    @SneakyThrows
    public void write(Map<String, Object> variables, String template, ServletOutputStream outputStream) {
        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process(template, context);
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }
}

package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.studentcart.CourseData;
import edu.hneu.studentsportal.domain.dto.studentcart.DisciplineData;
import edu.hneu.studentsportal.domain.dto.studentcart.DisciplineMarkData;
import edu.hneu.studentsportal.domain.dto.studentcart.SemesterData;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentCardService {
    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private DisciplineMarkService disciplineMarkService;


    public Student getStudent(long studentId) {
        return studentRepository.getOne(studentId);
    }

    public List<CourseData> getTableData(Student student) {
        return mapCourse(groupByCourse(student.getDisciplineMarks()));
    }

    private Map<Integer, List<DisciplineMark>> groupByCourse(List<DisciplineMark> value) {
        return value.stream().collect(Collectors.groupingBy(dm -> dm.getDiscipline().getCourse()));
    }

    private List<CourseData> mapCourse(Map<Integer, List<DisciplineMark>> courseGrouped) {
        return courseGrouped.entrySet().stream()
                .map(entry -> new CourseData(entry.getKey(), mapSemester(groupBySemester(entry.getValue()))))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<DisciplineMark>> groupBySemester(List<DisciplineMark> value) {
        return value.stream().collect(Collectors.groupingBy(dm -> dm.getDiscipline().getSemester()));
    }

    private List<SemesterData> mapSemester(Map<Integer, List<DisciplineMark>> semesterGrouped) {
        return semesterGrouped.entrySet().stream()
                .map(entry -> new SemesterData(entry.getKey(), mapDisciplineMarks(entry.getValue())))
                .collect(Collectors.toList());
    }

    private List<DisciplineMarkData> mapDisciplineMarks(List<DisciplineMark> disciplineMarks) {
        return disciplineMarks.stream().map(this::mapDisciplineMark).collect(Collectors.toList());
    }

    private DisciplineMarkData mapDisciplineMark(DisciplineMark disciplineMark) {
        return new DisciplineMarkData(mapDiscipline(disciplineMark.getDiscipline()), disciplineMark.getMark());
    }

    private DisciplineData mapDiscipline(Discipline discipline) {
        return new DisciplineData(discipline.getCode(),
                discipline.getCredits(),
                discipline.getControlForm(),
                discipline.getLabel(),
                discipline.isSecondary(),
                discipline.getType());
    }
}

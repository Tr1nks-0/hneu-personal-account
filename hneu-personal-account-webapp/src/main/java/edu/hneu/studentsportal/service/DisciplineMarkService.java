package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
public class DisciplineMarkService {

    public List<DisciplineMark> getStudentMarks(Student student, Integer course) {
        Predicate<DisciplineMark> hasGivenCourse = m -> Objects.equals(m.getDiscipline().getCourse(), course);
        return student.getDisciplineMarks().stream()
                .filter(m -> nonNull(m.getDiscipline()))
                .filter(hasGivenCourse).collect(toList());
    }

    public List<Integer> getCourses(Student student) {
        return extract(student.getDisciplineMarks(), m -> m.getDiscipline().getCourse()).stream().distinct().collect(toList());
    }


    public <E> List<E> extract(Collection<DisciplineMark> marks, Function<DisciplineMark, E> extractor) {
        return marks.stream().filter(m -> nonNull(m.getDiscipline())).map(extractor).collect(toList());
    }

    public Map<Integer, List<DisciplineMark>> getStudentMarksGroupedBySemester(List<DisciplineMark> marks) {
        Function<DisciplineMark, Integer> extractSemester = m -> m.getDiscipline().getSemester();
        return marks.stream().collect(Collectors.groupingBy(extractSemester));
    }
}

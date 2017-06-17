package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface DisciplineMarkService {

    List<DisciplineMark> getStudentMarks(Student student, int course, int semester);

    List<Discipline> getPossibleNewDisciplinesForStudent(Student student, int course, int semester);

    List<Integer> getStudentCourses(Student student);

    List<DisciplineMark> alignStudentDisciplinesMark(Student student, List<DisciplineMark> disciplineMarks);

    List<DisciplineMark> updateStudentMarks(Student student, List<DisciplineMark> importedMarks);

    <E> List<E> extract(Collection<DisciplineMark> marks, Function<DisciplineMark, E> converter);

}

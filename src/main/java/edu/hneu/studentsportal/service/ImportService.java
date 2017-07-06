package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ImportService {

    Student importStudent(File file);

    Map<Student, List<DisciplineMark>> importStudentMarks(File file);

    Map<Student, List<Discipline>> importStudentsChoice(File file);

}

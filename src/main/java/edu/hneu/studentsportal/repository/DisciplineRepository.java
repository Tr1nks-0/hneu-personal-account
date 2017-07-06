package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {


    Optional<Discipline> findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgramAndCreditsAndControlForm(String label, Integer course, Integer semester,
                                                                                                                 Speciality speciality, EducationProgram educationProgram,
                                                                                                                 Integer credits, DisciplineFormControl controlForm);

    Optional<Discipline> findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(String label, Integer course, Integer semester,
                                                                                         Speciality speciality, EducationProgram educationProgram);

    List<Discipline> findByCourseAndSemesterAndSpecialityAndEducationProgram(Integer course, Integer semester,
                                                                             Speciality speciality, EducationProgram educationProgram);

    Optional<Discipline> findByCodeAndCourseAndSemesterAndSpecialityAndEducationProgram(String code, int course, int semester, Speciality speciality, EducationProgram educationProgram);
}

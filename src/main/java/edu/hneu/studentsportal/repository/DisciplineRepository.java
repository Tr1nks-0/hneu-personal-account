package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {


    Optional<Discipline> findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgramAndCreditsAndControlForm(String label, int course, int semester,
                                                                                                                 Speciality speciality, EducationProgram educationProgram,
                                                                                                                 Integer credits, DisciplineFormControl controlForm);


    Optional<Discipline> findByLabelAndCourseAndSemesterAndCreditsAndControlForm(String label, int course, int semester,
                                                                                 int credits, DisciplineFormControl controlForm);

}

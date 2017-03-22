package edu.hneu.studentsportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {


    Optional<Discipline> findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgramAndCreditsAndControlForm(String label, Integer course, Integer semester,
                                                                                                                 Speciality speciality, EducationProgram educationProgram,
                                                                                                                 Integer credits, DisciplineFormControl controlForm);

}

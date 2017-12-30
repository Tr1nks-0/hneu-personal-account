package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineMarkRepository extends JpaRepository<DisciplineMark, Long> {

    List<DisciplineMark> findByDiscipline(Discipline discipline);

}

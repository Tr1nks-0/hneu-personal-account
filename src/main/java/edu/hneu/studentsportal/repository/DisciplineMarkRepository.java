package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.DisciplineMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineMarkRepository extends JpaRepository<DisciplineMark, Long> {

}

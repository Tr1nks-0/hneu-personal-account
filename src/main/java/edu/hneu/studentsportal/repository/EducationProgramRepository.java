package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.EducationProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationProgramRepository extends JpaRepository<EducationProgram, Long> {
    Optional<EducationProgram> findByName(String name);
}

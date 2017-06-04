package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);

    Optional<Group> findById(Long group);

    List<Group> findBySpecialityAndEducationProgram(Speciality speciality, EducationProgram educationProgram);
}

package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);

    List<Group> findBySpecialityAndEducationProgram(Speciality speciality, EducationProgram educationProgram);
}

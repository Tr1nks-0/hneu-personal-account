package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, String> {

    Optional<StudentProfile> findByEmail(String email);

}

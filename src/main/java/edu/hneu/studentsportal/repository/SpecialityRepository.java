package edu.hneu.studentsportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findByNameAndFaculty(String specialityName, Faculty faculty);

    List<Speciality> findByFaculty(Faculty faculty);
}

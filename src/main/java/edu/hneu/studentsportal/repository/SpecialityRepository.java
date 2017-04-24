package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findByNameAndFaculty(String specialityName, Faculty faculty);

    List<Speciality> findByFaculty(Faculty faculty);

    @Query(value = "SELECT 1 * s.faculty_id FROM speciality s JOIN faculty f ON s.faculty_id=f.id WHERE s.faculty_id=:facultyId", nativeQuery = true)
    Optional<BigInteger> checkFacultyHasSpecialities(@Param("facultyId") Long facultyId);

    @Query(value = "SELECT 1* s.faculty_id FROM speciality s JOIN faculty f ON s.faculty_id=f.id", nativeQuery = true)
    Optional<BigInteger> findFirstFacultyWithSpecialities();

}


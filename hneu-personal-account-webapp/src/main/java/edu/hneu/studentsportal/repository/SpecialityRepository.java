package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findById(Long id);

    Optional<Speciality> findByCode(String code);

    List<Speciality> findAllByFacultyId(Long facultyId);

    @Query(value = "SELECT 1 * s.faculty_id FROM speciality s JOIN faculty f ON s.faculty_id=f.id WHERE s.faculty_id=:facultyId LIMIT 1", nativeQuery = true)
    Optional<BigInteger> checkFacultyHasSpecialities(@Param("facultyId") Long facultyId);

    @Query(value = "SELECT 1 * s.faculty_id FROM speciality s JOIN faculty f ON s.faculty_id=f.id LIMIT 1", nativeQuery = true)
    Optional<BigInteger> findFirstFacultyIdWithSpecialities();

    @Query(value = "SELECT * FROM speciality WHERE faculty_id=:facultyId LIMIT 1", nativeQuery = true)
    Speciality findFirstSpecialityForFaculty(@Param("facultyId") Long facultyId);

}


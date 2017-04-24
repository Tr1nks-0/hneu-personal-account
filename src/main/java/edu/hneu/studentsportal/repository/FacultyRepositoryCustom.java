package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Faculty;

import java.util.Optional;

public interface FacultyRepositoryCustom {

    Optional<Faculty> findFacultyByIdOrDefault(Long id);

}

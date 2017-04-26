package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Faculty;

import java.util.Optional;

public interface FacultyRepositoryCustom {

    Faculty findByIdWithSpecialitiesOrDefault(Long id);

    Faculty findByIdOrDefault(Long id);

}

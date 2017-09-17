package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Faculty;

public interface FacultyRepositoryCustom {

    Faculty findByIdWithSpecialitiesOrDefault(Long id);

}

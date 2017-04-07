package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> getAllFaculties();

    Faculty getFaculty(long id);

    void save(Faculty faculty);

    void delete(long id);
}

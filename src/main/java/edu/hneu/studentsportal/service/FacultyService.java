package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> getAllFaculties();

    Faculty getFaculty(long faculty);
}

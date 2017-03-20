package edu.hneu.studentsportal.service;

import java.util.List;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;

public interface SpecialityService {
    List<Speciality> getSpecialitiesForFaculty(Faculty faculty);
}

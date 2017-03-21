package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;

import java.util.List;

public interface SpecialityService {
    List<Speciality> getSpecialitiesForFaculty(Faculty faculty);

    Speciality getSpeciality(long speciality);
}

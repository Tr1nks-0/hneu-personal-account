package edu.hneu.studentsportal.service;

import java.util.List;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;

public interface EducationProgramService {
    List<EducationProgram> getEducationProgramsForSpeciality(Speciality speciality);
}

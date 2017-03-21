package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;

import java.util.List;

public interface EducationProgramService {
    List<EducationProgram> getEducationProgramsForSpeciality(Speciality speciality);

    EducationProgram getEducationProgram(long id);
}

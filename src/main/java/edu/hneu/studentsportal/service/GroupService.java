package edu.hneu.studentsportal.service;

import java.util.List;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Speciality;

public interface GroupService {
    List<Group> getGroups(Speciality speciality, EducationProgram educationProgram);
}

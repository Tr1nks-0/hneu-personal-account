package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Speciality;

import java.util.List;

public interface GroupService {
    List<Group> getGroups(Speciality speciality, EducationProgram educationProgram);

    Group getGroup(long id);
}

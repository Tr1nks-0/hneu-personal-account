package edu.hneu.studentsportal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.GroupService;

@Service
public class DefaultGroupService implements GroupService {

    @Resource
    private GroupRepository groupRepository;

    @Override
    public List<Group> getGroups(Speciality speciality, EducationProgram educationProgram) {
        return groupRepository.findBySpecialityAndEducationProgram(speciality, educationProgram);
    }
}

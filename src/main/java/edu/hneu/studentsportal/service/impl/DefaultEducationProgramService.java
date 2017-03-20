package edu.hneu.studentsportal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.service.EducationProgramService;

@Service
public class DefaultEducationProgramService implements EducationProgramService {

    @Resource
    private EducationProgramRepository educationProgramRepository;

    @Override
    public List<EducationProgram> getEducationProgramsForSpeciality(Speciality speciality) {
        return educationProgramRepository.findBySpeciality(speciality);
    }
}

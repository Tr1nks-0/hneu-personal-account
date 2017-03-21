package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.service.EducationProgramService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultEducationProgramService implements EducationProgramService {

    @Resource
    private EducationProgramRepository educationProgramRepository;

    @Override
    public List<EducationProgram> getEducationProgramsForSpeciality(Speciality speciality) {
        return educationProgramRepository.findBySpeciality(speciality);
    }

    @Override
    public EducationProgram getEducationProgram(long id) {
        return educationProgramRepository.findOne(id);
    }
}

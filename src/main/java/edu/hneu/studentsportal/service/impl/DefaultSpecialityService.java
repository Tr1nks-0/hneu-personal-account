package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.SpecialityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultSpecialityService implements SpecialityService {

    @Resource
    private SpecialityRepository specialityRepository;

    @Override
    public List<Speciality> getSpecialitiesForFaculty(Faculty faculty) {
        return specialityRepository.findByFaculty(faculty);
    }

    @Override
    public Speciality getSpeciality(long speciality) {
        return specialityRepository.findOne(speciality);
    }
}

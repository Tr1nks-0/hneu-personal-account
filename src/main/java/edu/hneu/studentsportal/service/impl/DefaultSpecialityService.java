package edu.hneu.studentsportal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.SpecialityService;

@Service
public class DefaultSpecialityService implements SpecialityService {

    @Resource
    private SpecialityRepository specialityRepository;

    @Override
    public List<Speciality> getSpecialitiesForFaculty(Faculty faculty) {
        return specialityRepository.findByFaculty(faculty);
    }
}

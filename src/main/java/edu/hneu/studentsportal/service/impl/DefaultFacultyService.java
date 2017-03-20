package edu.hneu.studentsportal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.service.FacultyService;

@Service
public class DefaultFacultyService implements FacultyService {

    @Resource
    private FacultyRepository facultyRepository;

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
}

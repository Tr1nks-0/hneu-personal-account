package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.service.FacultyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultFacultyService implements FacultyService {

    @Resource
    private FacultyRepository facultyRepository;

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findOne(id);
    }

    @Override
    public void save(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Override
    public void delete(long id) {
        facultyRepository.delete(id);
    }
}

package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpecialityService {

    @Resource
    private SpecialityRepository specialityRepository;

    public Speciality findByIdOrDefault(Long specialityId, Faculty faculty) {
        return specialityRepository.findById(specialityId)
                .orElseGet(() -> specialityRepository.findFirstSpecialityForFaculty(faculty.getId()));
    }
}

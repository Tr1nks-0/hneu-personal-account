package edu.hneu.studentsportal.repository.impl;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.repository.SpecialityRepositoryCustom;

import javax.annotation.Resource;

public class SpecialityRepositoryImpl implements SpecialityRepositoryCustom {

    @Resource
    private SpecialityRepository specialityRepository;

    @Override
    public Speciality findByIdOrDefault(Long specialityId, Faculty faculty) {
        return specialityRepository.findById(specialityId)
                .orElseGet(() -> specialityRepository.findFirstSpecialityForFaculty(faculty.getId()));
    }
}

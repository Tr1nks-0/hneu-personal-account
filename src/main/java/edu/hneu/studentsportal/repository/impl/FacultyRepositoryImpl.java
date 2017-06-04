package edu.hneu.studentsportal.repository.impl;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.FacultyRepositoryCustom;
import edu.hneu.studentsportal.repository.SpecialityRepository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.function.Supplier;

public class FacultyRepositoryImpl implements FacultyRepositoryCustom {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;

    @Override
    public Faculty findByIdWithSpecialitiesOrDefault(Long id) {
        return facultyRepository.findById(id)
                .filter(faculty -> specialityRepository.checkFacultyHasSpecialities(faculty.getId()).isPresent())
                .orElseGet(getFirstFacultyWithSpecialities());
    }

    @Override
    public Faculty findByIdOrDefault(Long id) {
        return facultyRepository.findById(id).orElseGet(() -> facultyRepository.findFirst());
    }

    private Supplier<Faculty> getFirstFacultyWithSpecialities() {
        return () -> specialityRepository.findFirstFacultyIdWithSpecialities().map(BigInteger::longValue).map(facultyRepository::findOne).orElse(null);
    }
}

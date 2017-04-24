package edu.hneu.studentsportal.repository.impl;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.FacultyRepositoryCustom;
import edu.hneu.studentsportal.repository.SpecialityRepository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Optional;

public class FacultyRepositoryImpl implements FacultyRepositoryCustom {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;

    @Override
    public Optional<Faculty> findFacultyByIdOrDefault(Long facultyId) {
        return Optional.ofNullable(facultyId)
                .filter(id -> specialityRepository.checkFacultyHasSpecialities(id).isPresent())
                .map(facultyRepository::findById)
                .orElseGet(
                        () -> specialityRepository.findFirstFacultyWithSpecialities()
                                .map(BigInteger::longValue)
                                .map(facultyRepository::findOne)
                );
    }
}

package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.function.Supplier;

@Service
public class FacultyService {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;

    public Faculty findByIdWithSpecialitiesOrDefault(Long id) {
        return facultyRepository.findById(id)
                .filter(faculty -> specialityRepository.checkFacultyHasSpecialities(faculty.getId()).isPresent())
                .orElseGet(getFirstFacultyWithSpecialities());
    }

    private Supplier<Faculty> getFirstFacultyWithSpecialities() {
        return () -> specialityRepository.findFirstFacultyIdWithSpecialities()
                .map(BigInteger::longValue)
                .map(facultyRepository::findOne).orElse(null);
    }
}

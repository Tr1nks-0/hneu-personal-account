package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;

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

package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Speciality;

public interface SpecialityRepositoryCustom {

    Speciality findByIdOrDefault(Long specialityId, Faculty faculty);

}

package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;

public interface SpecialityRepositoryCustom {

    Speciality findByIdOrDefault(Long specialityId, Faculty faculty);

}

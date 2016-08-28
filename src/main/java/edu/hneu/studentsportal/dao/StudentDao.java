package edu.hneu.studentsportal.dao;

import edu.hneu.studentsportal.model.StudentProfile;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    StudentProfile findById(String id);

    void save(StudentProfile studentProfile);

    Optional<StudentProfile> findByEmail(String email);

    void remove(String id);

    List<StudentProfile> findAll();

    List<StudentProfile> find(String fullName, Integer page);

    long getPageCountForSearchCriteria(String searchCriteria);
}

package com.rozdolskyi.traininghneu.dao;

import com.rozdolskyi.traininghneu.model.StudentProfile;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    StudentProfile findById(String id);

    void save(StudentProfile studentProfile);

    Optional<StudentProfile> findByEmail(String email);

    List<StudentProfile> findAll();
}

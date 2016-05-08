package com.rozdolskyi.traininghneu.service;

import com.rozdolskyi.traininghneu.model.StudentProfile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentProfile readStudentProfilesFromFile(File file);

    void save(StudentProfile studentProfile);

    StudentProfile findStudentProfileById(String id);

    Optional<StudentProfile> findStudentProfileByEmail(String email);

    void updateStudentsScoresFromFile(File file);

    List<StudentProfile> findAll();

    void setCredentials(StudentProfile studentProfile);
}

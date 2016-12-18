package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.model.StudentProfile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentProfile readStudentProfilesFromFile(File file);

    void save(StudentProfile studentProfile);

    StudentProfile findStudentProfileById(String id);

    Optional<StudentProfile> findStudentProfileByEmail(String email);

    void updateStudentsScoresFromFile(File file);

    List<StudentProfile> find();

    void setCredentials(StudentProfile studentProfile);

    void setGroupId(StudentProfile studentProfile);

    void sendEmailAfterProfileCreation(StudentProfile studentProfile);

    void sendEmailAfterProfileUpdating(StudentProfile studentProfile);

    List<StudentProfile> find(String fullName, Integer page);

    long getPagesCountForSearchCriteria(String searchCriteria);

    void remove(String id);

    void refreshMarks();
}

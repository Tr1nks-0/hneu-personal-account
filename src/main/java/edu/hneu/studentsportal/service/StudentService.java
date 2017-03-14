package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Student;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student readStudentProfilesFromFile(File file);

    void save(Student studentProfile);

    Student findStudentProfileById(String id);

    Optional<Student> findStudentProfileByEmail(String email);

    void updateStudentsScoresFromFile(File file);

    List<Student> find();

    void setCredentials(Student studentProfile);

    void setGroupId(Student studentProfile);

    void sendEmailAfterProfileCreation(Student studentProfile);

    void sendEmailAfterProfileUpdating(Student studentProfile);

    List<Student> find(String fullName, Integer page);

    long getPagesCountForSearchCriteria(String searchCriteria);

    void remove(String id);

    void refreshMarks();
}

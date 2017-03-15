package edu.hneu.studentsportal.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import edu.hneu.studentsportal.entity.Student;

public interface StudentService {

    Student parseStudentProfile(File file);

    void save(Student studentProfile);

    Student findStudentProfileById(String id);

    Optional<Student> findStudentProfileByEmail(String email);

    void updateStudentsScoresFromFile(File file);

    List<Student> find();

    void setGroupId(Student studentProfile);

    void sendEmailAfterProfileCreation(Student studentProfile);

    void sendEmailAfterProfileUpdating(Student studentProfile);

    List<Student> find(String fullName, Integer page);

    long getPagesCountForSearchCriteria(String searchCriteria);

    void remove(String id);

    void refreshMarks();
}

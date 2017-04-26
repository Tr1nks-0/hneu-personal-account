package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Student;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    void sendEmailAfterProfileCreation(Student studentProfile);

    void sendEmailAfterProfileUpdating(Student studentProfile);
}

package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Student;

public interface EmailService {

    void sendProfileWasCreatedEmail(Student studentProfile);

    void sendProfileWasChangedEmail(Student studentProfile);

    void sendContactUsEmail(String from, String token, String message);
}


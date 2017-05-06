package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.entity.Student;

import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface EmailService {

    void send(MimeMessage message);

    void sendProfileWasCreatedEmail(Student studentProfile);

    void sendProfileWasChangedEmail(Student studentProfile);
}


package edu.hneu.studentsportal.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void send(MimeMessage message);

    String createHtmlFromVelocityTemplate(String velocityTemplate, Map model);

}


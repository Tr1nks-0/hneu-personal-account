package edu.hneu.studentsportal.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

@Service
public class MessageService {

    @Resource
    private MessageSource messageSource;

    //emails

    public String userWasCreatedEmailSubject() {
        return getMessage("mails.subject.new.user");
    }

    public String userWasChangedEmailSubject() {
        return getMessage("mails.subject.changed.user");
    }

    public String userWasChangedEmailBody() {
        return getMessage("mails.body.changed.user");
    }

    public String contactUsEmailSubject() {
        return getMessage("mails.subject.contact.us.email");
    }

    //errors

    public String studentExistsError() {
        return getMessage("error.student.profile.exists");
    }

    public String groupExistsError() {
        return getMessage("error.group.exists");
    }

    public String facultyExistsError() {
        return getMessage("error.faculty.exists");
    }

    public String specialityExistsError() {
        return getMessage("error.speciality.exists");
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, new Object[0], Locale.getDefault());
    }

    public String educationProgramExistsError() {
        return getMessage("error.education.program.exists");
    }

    public String disciplineExistsError() {
        return getMessage("error.discipline.exists");
    }
}

package edu.hneu.studentsportal.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

@Service
public class MessageService {

    @Resource
    private MessageSource messageSource;

    public String getUserWasCreatedEmailSubject() {
        return getMessage("mails.subject.new.user");
    }

    public String getUserWasChangedEmailSubject() {
        return getMessage("mails.subject.changed.user");
    }

    public String getUserWasChangedEmailBody() {
        return getMessage("mails.body.changed.user");
    }

    public String getContactUsEmailSubject() {
        return getMessage("mails.subject.contact.us.email");
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, new Object[0], Locale.getDefault());
    }
}

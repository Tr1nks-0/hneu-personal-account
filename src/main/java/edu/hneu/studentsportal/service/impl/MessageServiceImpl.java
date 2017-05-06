package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageSource messageSource;

    @Override
    public String getUserWasCreatedEmailSubject() {
        return getMessage("mails.subject.new.user");
    }

    @Override
    public String getUserWasChangedEmailSubject() {
        return getMessage("mails.subject.changed.user");
    }

    @Override
    public String getUserWasChangedEmailBody() {
        return getMessage("mails.body.changed.user");
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, new Object[0], Locale.getDefault());
    }
}

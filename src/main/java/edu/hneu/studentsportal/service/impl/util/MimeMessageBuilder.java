package edu.hneu.studentsportal.service.impl.util;

import lombok.SneakyThrows;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

import static org.apache.commons.lang.CharEncoding.UTF_8;

public class MimeMessageBuilder {
    private String from;
    private String to;
    private String subject;
    private String text;
    private boolean isHtml;

    public MimeMessageBuilder(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public MimeMessageBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MimeMessageBuilder text(String text, boolean isHtml) {
        this.text = text;
        this.isHtml = isHtml;
        return this;
    }

    @SneakyThrows
    public MimeMessage build(MimeMessage mimeMessage) {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, UTF_8);
        message.setFrom(new InternetAddress(from));
        message.setTo(new InternetAddress(to));
        message.setSubject(subject);
        message.setText(text, isHtml);
        message.setSentDate(new Date());
        return mimeMessage;
    }
}

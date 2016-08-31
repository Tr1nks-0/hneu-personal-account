package edu.hneu.studentsportal.service.impl;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;

@Service
public class DefaultEmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;
    @Value("${support.mail}")
    public String supportMail;
    @Value("${emails.integration.service.url}")
    public String emailsIntegrationServiceUrl;

    public void send(MimeMessage message) {
        mailSender.send(message);
    }

    public String createHtmlFromVelocityTemplate(String velocityTemplate, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplate, "UTF-8", model);
    }


    class MimeMessageBuilder {
        private String from;
        private String to;
        private String subject;
        private String text;
        private boolean isHtml;

        public MimeMessageBuilder(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public MimeMessageBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public MimeMessageBuilder setText(String text, boolean isHtml) {
            this.text = text;
            this.isHtml = isHtml;
            return this;
        }

        public MimeMessage build() {
            try {
                final MimeMessage mimeMessage = mailSender.createMimeMessage();
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(new InternetAddress(from));
                message.setTo(new InternetAddress(to));
                message.setSubject(subject);
                message.setText(text, isHtml);
                message.setSentDate(new Date());
                return mimeMessage;
            } catch (MessagingException e) {
                throw new RuntimeException("Cannot create mime-message: ", e);
            }
        }
    }

}

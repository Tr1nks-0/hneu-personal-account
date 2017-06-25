package edu.hneu.studentsportal.service.impl;

import com.google.common.collect.ImmutableMap;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.feature.SiteFeature;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.GmailService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.impl.util.MimeMessageBuilder;
import freemarker.template.Configuration;
import javaslang.control.Try;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Log4j
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private EmailServiceImpl emailService;
    @Resource
    private Configuration freeMarkerConfiguration;
    @Resource
    private MessageService messageService;
    @Resource
    private GmailService gmailService;

    @Value("${communication.mail.decan}")
    public String decanMail;
    @Value("${communication.mail.support.username}")
    public String supportMail;

    @Override
    public void sendProfileWasCreatedEmail(Student student) {
        if (SiteFeature.SEND_EMAIL_AFTER_PROFILE_CREATION.isActive()) {
            Map<String, Object> modelForTemplate = ImmutableMap.of("name", student.getName());
            String emailBody = emailService.buildHtmlForTemplate("profileWasCreatedEmailTemplate.ftl", modelForTemplate);
            MimeMessage message = new MimeMessageBuilder(supportMail, student.getEmail())
                    .subject(messageService.userWasCreatedEmailSubject())
                    .text(emailBody, true)
                    .build(mailSender.createMimeMessage());
            scheduleEmailSendingTask(message, mailSender::send);
        }
    }

    @Override
    public void sendProfileWasChangedEmail(Student student) {
        if (SiteFeature.SEND_EMAIL_AFTER_PROFILE_MODIFICATION.isActive()) {
            Map<String, Object> modelForTemplate = ImmutableMap.of("message", messageService.userWasChangedEmailBody());
            String emailBody = emailService.buildHtmlForTemplate("profileWasChangedEmailTemplate.ftl", modelForTemplate);
            MimeMessage message = new MimeMessageBuilder(supportMail, student.getEmail())
                    .subject(messageService.userWasChangedEmailSubject())
                    .text(emailBody, true)
                    .build(mailSender.createMimeMessage());
            scheduleEmailSendingTask(message, mailSender::send);
        }
    }

    @Override
    public void sendContactUsEmail(String from, String token, String text) {
        MimeMessage message = new MimeMessageBuilder(from, decanMail)
                .subject(messageService.contactUsEmailSubject())
                .text(text, false)
                .build(mailSender.createMimeMessage());
        scheduleEmailSendingTask(message, m -> gmailService.sendEmail(from, token, gmailService.convertToGmailMessage(m)));
    }

    @SneakyThrows
    private String buildHtmlForTemplate(String template, Map model) {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate(template), model);
    }

    private void scheduleEmailSendingTask(MimeMessage message, Consumer<MimeMessage> emailSender) {
        Executors.newCachedThreadPool().execute(() ->
                Try.run(() -> emailSender.accept(message))
                        .onFailure(e -> log.warn("Failed during sending email: " + e.getMessage(), e)));
    }

}

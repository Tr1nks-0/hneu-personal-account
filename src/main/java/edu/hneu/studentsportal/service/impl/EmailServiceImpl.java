package edu.hneu.studentsportal.service.impl;

import com.google.common.collect.ImmutableMap;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.service.EmailService;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.impl.util.MimeMessageBuilder;
import freemarker.template.Configuration;
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

    @Value("${decan.mail}")
    public String decanMail;
    @Value("${support.mail}")
    public String supportMail;


    @Override
    public void send(final MimeMessage message) {
        mailSender.send(message);
    }

    @Override
    public void sendProfileWasCreatedEmail(Student student) {
        Map<String, Object> modelForTemplate = ImmutableMap.of("name", student.getName());
        String emailBody = emailService.buildHtmlForTemplate("profileWasCreatedEmailTemplate.ftl", modelForTemplate);
        MimeMessage message = new MimeMessageBuilder(supportMail, student.getEmail())
                .subject(messageService.getUserWasCreatedEmailSubject())
                .text(emailBody, true)
                .build(mailSender.createMimeMessage());
        scheduleEmailSendingTask(message, mailSender::send);
    }

    @Override
    public void sendProfileWasChangedEmail(Student student) {
        Map<String, Object> modelForTemplate = ImmutableMap.of("message", messageService.getUserWasChangedEmailBody());
        String emailBody = emailService.buildHtmlForTemplate("profileWasChangedEmailTemplate.ftl", modelForTemplate);
        MimeMessage message = new MimeMessageBuilder(supportMail, student.getEmail())
                .subject(messageService.getUserWasChangedEmailSubject())
                .text(emailBody, true)
                .build(mailSender.createMimeMessage());
        scheduleEmailSendingTask(message, mailSender::send);
    }

    @Override
    public void sendContactUsEmail(Student student, String text) {
        MimeMessage message = new MimeMessageBuilder(student.getEmail(), decanMail)
                .subject(messageService.getContactUsEmailSubject())
                .text(text, false)
                .build(mailSender.createMimeMessage());
        scheduleEmailSendingTask(message, m -> gmailService.sendEmail(student.getEmail(), gmailService.convertToGmailMessage(m)));
    }

    @SneakyThrows
    private String buildHtmlForTemplate(String template, Map model) {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate(template), model);
    }

    private void scheduleEmailSendingTask(MimeMessage message, Consumer<MimeMessage> emailSender) {
        Executors.newCachedThreadPool().execute(() -> {
            try {
                emailSender.accept(message);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        });
    }

}

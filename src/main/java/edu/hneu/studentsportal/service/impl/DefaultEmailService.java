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
import java.util.function.Supplier;

@Log4j
@Service
public class DefaultEmailService implements EmailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private DefaultEmailService emailService;
    @Resource
    private Configuration freeMarkerConfiguration;
    @Resource
    private MessageService messageService;

    @Value("${support.mail}")
    public String supportMail;

    @Override
    public void send(final MimeMessage message) {
        mailSender.send(message);
    }

    @Override
    public void sendProfileWasCreatedEmail(Student studentProfile) {
        sendEmail(() -> {
            Map<String, Object> modelForTemplate = ImmutableMap.of("name", studentProfile.getName());
            String emailBody = emailService.buildHtmlForTemplate("profileWasCreatedEmailTemplate.ftl", modelForTemplate);
            return new MimeMessageBuilder(supportMail, studentProfile.getEmail())
                    .subject(messageService.getUserWasCreatedEmailSubject())
                    .text(emailBody, true)
                    .build(mailSender.createMimeMessage());
        });
    }

    @Override
    public void sendProfileWasChangedEmail(Student studentProfile) {
        sendEmail(() -> {
            Map<String, Object> modelForTemplate = ImmutableMap.of("message", messageService.getUserWasChangedEmailBody());
            String emailBody = emailService.buildHtmlForTemplate("profileWasChangedEmailTemplate.ftl", modelForTemplate);
            return new MimeMessageBuilder(supportMail, studentProfile.getEmail())
                    .subject(messageService.getUserWasChangedEmailSubject())
                    .text(emailBody, true)
                    .build(mailSender.createMimeMessage());
        });
    }

    private void sendEmail(Supplier<MimeMessage> mimeMessageSupplier) {
        Executors.newCachedThreadPool().execute(() -> {
            try {
                mailSender.send(mimeMessageSupplier.get());
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        });
    }

    @SneakyThrows
    private String buildHtmlForTemplate(String template, Map model) {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate(template), model);
    }

}

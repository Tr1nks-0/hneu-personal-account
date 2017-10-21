package edu.hneu.studentsportal.service;

import com.google.common.collect.ImmutableMap;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.feature.SiteFeature;
import freemarker.template.Configuration;
import javaslang.control.Try;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.apache.commons.lang.CharEncoding.UTF_8;

@Log4j
@Service
public class EmailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private EmailService emailService;
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

    public void sendProfileWasCreatedEmail(Student student) {
        if (SiteFeature.SEND_EMAIL_AFTER_PROFILE_CREATION.isActive()) {
            Map<String, Object> modelForTemplate = ImmutableMap.of("name", student.getName());
            String emailBody = emailService.buildHtmlForTemplate("profileWasCreatedEmailTemplate.ftl", modelForTemplate);
            MimeMessage message = mimeMessageBuilder().of(mailSender.createMimeMessage())
                    .from(supportMail)
                    .to(student.getEmail())
                    .subject(messageService.userWasCreatedEmailSubject())
                    .text(emailBody)
                    .isHtml(true)
                    .build();
            scheduleEmailSendingTask(message, mailSender::send);
        }
    }

    public void sendProfileWasChangedEmail(Student student) {
        if (SiteFeature.SEND_EMAIL_AFTER_PROFILE_MODIFICATION.isActive()) {
            Map<String, Object> modelForTemplate = ImmutableMap.of("message", messageService.userWasChangedEmailBody());
            String emailBody = emailService.buildHtmlForTemplate("profileWasChangedEmailTemplate.ftl", modelForTemplate);
            MimeMessage message = mimeMessageBuilder().of(mailSender.createMimeMessage())
                    .from(supportMail)
                    .to(student.getEmail())
                    .subject(messageService.userWasChangedEmailSubject())
                    .text(emailBody)
                    .isHtml(true)
                    .build();
            scheduleEmailSendingTask(message, mailSender::send);
        }
    }

    public void sendContactUsEmail(String from, String token, String text) {
        MimeMessage message = mimeMessageBuilder().of(mailSender.createMimeMessage())
                .from(from)
                .to(decanMail)
                .subject(messageService.contactUsEmailSubject())
                .text(text)
                .isHtml(false)
                .build();
        scheduleEmailSendingTask(message, m -> gmailService.sendEmail(from, token, gmailService.convertToGmailMessage(m)));
    }

    @SneakyThrows
    private String buildHtmlForTemplate(String template, Map model) {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate(template), model);
    }

    private void scheduleEmailSendingTask(MimeMessage message, Consumer<MimeMessage> emailSender) {
        Executors.newCachedThreadPool().execute(() -> Try.run(() -> emailSender.accept(message))
                .onFailure(e -> log.warn("Failed during sending email: " + e.getMessage(), e)));
    }

    @Builder(builderMethodName = "mimeMessageBuilder")
    @SneakyThrows
    private static MimeMessage buildMimeMessage(MimeMessage of, String from, String to, String subject, String text, boolean isHtml) {
        MimeMessageHelper message = new MimeMessageHelper(of, UTF_8);
        message.setFrom(new InternetAddress(from));
        message.setTo(new InternetAddress(to));
        message.setSubject(subject);
        message.setText(text, isHtml);
        message.setSentDate(new Date());
        return of;
    }

}

package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.StudentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Log4j
@Service
public class DefaultStudentService implements StudentService {

    private static final String SEND_PASSWORD_VM_TEMPLATE = "velocity/sendProfileWasCreated.vm";
    private static final String SEND_PROFILE_WAS_CHANGED_VM_TEMPLATE = "velocity/sendProfileWasChangedMessage.vm";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DefaultEmailService emailService;
    @Autowired
    private GmailService gmailService;
    @Autowired
    private StudentRepository studentRepository;

    @Value("${support.mail}")
    public String supportMail;

    @Override
    public void sendEmailAfterProfileCreation(final Student studentProfile) {
        /*try {
            final Map<String, Object> modelForVelocity = ImmutableMap.of("name", studentProfile.getName());
            //@formatter:off
            final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(
                    supportMail, studentProfile.getEmail())
                    .setSubject("Кабінет студента | Вхід")
                    .setText(emailService.createHtmlFromVelocityTemplate(SEND_PASSWORD_VM_TEMPLATE, modelForVelocity), true)
                    .build();
            //@formatter:on
            gmailService.api().users().messages().send(supportMail, gmailService.convertToGmailMessage(mimeMessage)).execute();
        } catch (final Exception e) {
            LOG.warn(e);
        }*/
    }

    @Override
    public void sendEmailAfterProfileUpdating(final Student studentProfile) {
        /*
         * try { final Map<String, Object> modelForVelocity = ImmutableMap.of("message", "Ваш профіль був оновлений. Перейдійть у кабінет для перегляду.");
         * final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(supportMail, studentProfile.getEmail())
         * .setSubject("Кабінет студента | Зміни в профілі") .setText(emailService.createHtmlFromVelocityTemplate(SEND_PROFILE_WAS_CHANGED_VM_TEMPLATE,
         * modelForVelocity), true).build(); gmailService.api().users().messages().send(supportMail, gmailService.convertToGmailMessage(mimeMessage)).execute();
         * } catch (final Exception e) { LOG.warn(e); }
         */
    }
}

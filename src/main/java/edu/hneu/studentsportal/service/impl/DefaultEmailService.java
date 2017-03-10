package edu.hneu.studentsportal.service.impl;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.service.EmailService;

@Service
public class DefaultEmailService implements EmailService {

  /*  private static final String UTF_8 = "UTF-8";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void send(final MimeMessage message) {
        mailSender.send(message);
    }

    @Override
    public String createHtmlFromVelocityTemplate(final String velocityTemplate, final Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplate, UTF_8, model);
    }

    public class MimeMessageBuilder {

        private final String from;
        private final String to;
        private String subject;
        private String text;
        private boolean isHtml;

        public MimeMessageBuilder(final String from, final String to) {
            this.from = from;
            this.to = to;
        }

        public MimeMessageBuilder setSubject(final String subject) {
            this.subject = subject;
            return this;
        }

        public MimeMessageBuilder setText(final String text, final boolean isHtml) {
            this.text = text;
            this.isHtml = isHtml;
            return this;
        }

        public MimeMessage build() {
            try {
                final MimeMessage mimeMessage = mailSender.createMimeMessage();
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, UTF_8);
                message.setFrom(new InternetAddress(from));
                message.setTo(new InternetAddress(to));
                message.setSubject(subject);
                message.setText(text, isHtml);
                message.setSentDate(new Date());
                return mimeMessage;
            } catch (final MessagingException e) {
                throw new RuntimeException("Cannot create mime-message: ", e);
            }
        }
    }*/

}

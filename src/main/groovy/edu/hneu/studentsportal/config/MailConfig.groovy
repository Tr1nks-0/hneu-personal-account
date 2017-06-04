package edu.hneu.studentsportal.config

import org.apache.commons.lang.StringUtils
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver

@Configuration
@EnableAutoConfiguration
class MailConfig {

    @Bean
    JavaMailSender getMailSender(Environment environment) {
        new JavaMailSenderImpl(
                host: 'smtp.gmail.com',
                port: 587,
                username: environment.getRequiredProperty('communication.server.username'),
                password: environment.getRequiredProperty('communication.server.password'),
                javaMailProperties: [
                        'mail.smtp.starttls.enable': 'true',
                        'mail.smtp.auth'           : 'true',
                        'mail.transport.protocol'  : 'smtp',
                ]
        )
    }

    @Bean
    FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        new FreeMarkerConfigurationFactoryBean()
    }


    @Bean
    FreeMarkerViewResolver freemarkerViewResolver() {
        new FreeMarkerViewResolver(
                prefix: StringUtils.EMPTY,
                suffix: '.ftl'
        )
    }

}

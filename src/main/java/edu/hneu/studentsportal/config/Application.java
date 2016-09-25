package edu.hneu.studentsportal.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/root-context.xml")
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

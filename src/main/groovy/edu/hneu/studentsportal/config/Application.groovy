package edu.hneu.studentsportal.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

@SpringBootApplication
@ImportResource("classpath:spring/root-context.xml")
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
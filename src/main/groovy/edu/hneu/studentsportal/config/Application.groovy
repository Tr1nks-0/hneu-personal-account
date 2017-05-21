package edu.hneu.studentsportal.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {

    static void main(String[] args) {
        SpringApplication.run([Application.class, 'classpath:application.groovy'].toArray(), args)
    }
}
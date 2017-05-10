package edu.hneu.studentsportal.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.web.context.request.RequestContextListener

import javax.servlet.ServletContext
import javax.servlet.ServletException

@SpringBootApplication
class Application {

    static void main(String[] args) {
        SpringApplication.run([Application.class, 'classpath:application.groovy'].toArray(), args)
    }
}
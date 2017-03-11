package edu.hneu.studentsportal.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.InternalResourceViewResolver

@EnableWebMvc
@ComponentScan
@Configuration
class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    InternalResourceViewResolver viewResolver() {
        new InternalResourceViewResolver(
                prefix: '/WEB-INF/views/',
                suffix: '.jsp'
        )
    }

    @Bean
    DispatcherServlet dispatcherServlet() {
        new DispatcherServlet()
    }

    @Override
    void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable()
    }

    @Bean
    RequestContextListener requestContextListener() {
        new RequestContextListener()
    }

}

package edu.hneu.studentsportal.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.filter.RequestContextFilter
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

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    RequestContextListener requestContextListener() {
        new RequestContextListener()
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextFilter.class)
    RequestContextFilter requestContextFilter() {
        new RequestContextFilter()
    }

    @Override
    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable()
    }

}

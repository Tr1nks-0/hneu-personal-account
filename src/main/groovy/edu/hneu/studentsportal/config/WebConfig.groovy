package edu.hneu.studentsportal.config

import edu.hneu.studentsportal.service.MessageService
import edu.hneu.studentsportal.utils.annotation.Message
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.util.ClassUtils
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.filter.RequestContextFilter
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.InternalResourceViewResolver

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

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

    @Bean
    MessageService messageService(MessageSource messageSource) {
        Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), [MessageService.class] as Class<?>[], new InvocationHandler() {
            @Override
            Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.isAnnotationPresent(Message.class)) {
                    Message annotation = method.getAnnotation(Message.class)
                    messageSource.getMessage(annotation.value(), args, Locale.getDefault())
                } else {
                    //stub for hashCode, equals, toString, etc. methods of the interface
                    method.invoke(this, args)
                }
            }
        }) as MessageService
    }
}

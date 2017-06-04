package edu.hneu.studentsportal.utils.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Message {

    String value();

    String[] args() default {};
}

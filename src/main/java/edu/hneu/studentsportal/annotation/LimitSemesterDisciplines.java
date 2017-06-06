package edu.hneu.studentsportal.annotation;


import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.validator.LimitSemesterDisciplinesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LimitSemesterDisciplinesValidator.class)
public @interface LimitSemesterDisciplines {

    int count() default 1;

    DisciplineType type();

    String message() default "{limit.semester.disciplines.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

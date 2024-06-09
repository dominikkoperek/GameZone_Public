package com.example.gamezoneproject.validation.other.containsh2;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String contains "<h2></h2>".
 */
@Documented
@Constraint(validatedBy = ContainsH2Validator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ContainsH2 {
    String message () default "{jakarta.validation.constraints.ContainsH2.message}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

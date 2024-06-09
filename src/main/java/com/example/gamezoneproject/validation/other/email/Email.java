package com.example.gamezoneproject.validation.other.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


    /**
     * Checks if the Email is correct.
     */
    @Documented
    @Constraint(validatedBy = EmailValidator.class)
    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    public @interface Email {
        String message() default "{jakarta.validation.constraints.Email.message}";
        Class<?>[] groups() default {};
        int min() default 0;

        int max() default 9999;

        Class<? extends Payload>[] payload() default {};
    }

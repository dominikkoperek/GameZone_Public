package com.example.gamezoneproject.domain.validation.password;

import com.example.gamezoneproject.domain.validation.file.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


    /**
     * Checks if the Password is password contains all necessary chars.
     */
    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    public @interface Password {
        String message() default "{jakarta.validation.constraints.Password.message}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
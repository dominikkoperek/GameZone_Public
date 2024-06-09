package com.example.gamezoneproject.validation.country;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String Country exists.
 */
@Documented
@Constraint(validatedBy = CountryNotExistsValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CountryNotExists {
    String message() default "{jakarta.validation.constraints.CountryNotExists.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


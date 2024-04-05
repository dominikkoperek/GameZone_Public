package com.example.gamezoneproject.domain.validation.other.svg;

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
@Constraint(validatedBy = SvgImageValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface SvgImage {
    String message() default "{jakarta.validation.constraints.SvgImage.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


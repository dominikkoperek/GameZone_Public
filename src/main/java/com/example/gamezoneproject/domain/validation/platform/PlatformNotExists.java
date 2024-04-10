package com.example.gamezoneproject.domain.validation.platform;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the Set of platforms exists.
 */
@Documented
@Constraint(validatedBy = PlatformNotExistsValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PlatformNotExists {
    String message () default "{jakarta.validation.constraints.PlatformNotExists.message}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
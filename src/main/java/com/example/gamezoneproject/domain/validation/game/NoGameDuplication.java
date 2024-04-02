package com.example.gamezoneproject.domain.validation.game;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String name of the game already exists
 */
@Documented
@Constraint(validatedBy = NoGameDuplicationValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NoGameDuplication {
    String message () default "{jakarta.validation.constraints.NoGameDuplication.message}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

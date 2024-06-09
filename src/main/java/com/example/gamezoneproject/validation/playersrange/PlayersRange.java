package com.example.gamezoneproject.validation.playersrange;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if Players range is in range and valid.
 */
@Documented
@Constraint(validatedBy = PlayersRangeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PlayersRange {
    int rangeMin() default 1;
    int rangeMax () default 9999;
    int maxMin () default 9999;

    String message() default "{jakarta.validation.constraints.PlayersRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

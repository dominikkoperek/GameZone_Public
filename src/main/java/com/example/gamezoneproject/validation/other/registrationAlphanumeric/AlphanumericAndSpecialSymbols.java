package com.example.gamezoneproject.validation.other.registrationAlphanumeric;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String contains only unicode letters and digits.
 */
@Documented
@Constraint(validatedBy = AlphanumericAndSpecialSymbolsValidator.class)
@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
public @interface AlphanumericAndSpecialSymbols {
    String message() default "{jakarta.validation.constraints.AlphanumericAndSpecialSymbols.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.example.gamezoneproject.validation.other.illegalexpression;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String contains illegal value's (like "<script>" or "<h1><h3><h4><h5><h6>).
 */
@Documented
@Constraint(validatedBy = NoIllegalExpressionValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NoIllegalExpression {
    String message() default "{jakarta.validation.constraints.NoIllegalExpression.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

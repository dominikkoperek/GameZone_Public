package com.example.gamezoneproject.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Checks if 2 fields are the same.
 */
@Documented
@Constraint(validatedBy = FieldEqualField2Validator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface FieldEqualField2 {
    String message() default "{jakarta.validation.constraints.FieldEqualField2.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String field1 () ;
    String field2 () ;
}

package com.example.gamezoneproject.domain.validation.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Check if image MIME extension match the filename extension.
 */
@Documented
@Constraint(validatedBy = CorruptedImageExtensionValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CorruptedImageExtension {
    String message() default "{jakarta.validation.constraints.CorruptedImageExtension.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

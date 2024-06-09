package com.example.gamezoneproject.validation.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Checks if the Image width in px is in range.
 */
@Documented
@Constraint(validatedBy = ImageWidthValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ImageWidth {
    int minImageWidth() default 100;
    int maxImageWidth() default 600;
    String message() default "{jakarta.validation.constraints.ImageWidth.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
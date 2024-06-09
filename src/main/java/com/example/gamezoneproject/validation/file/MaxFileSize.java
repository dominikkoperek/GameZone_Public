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
 * Checks if the File size is not too big.
 */
@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface MaxFileSize {
    int maxSizeMb() default 0;
    String message() default "{jakarta.validation.constraints.MainCategoryExists.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

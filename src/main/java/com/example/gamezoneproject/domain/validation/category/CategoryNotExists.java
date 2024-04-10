package com.example.gamezoneproject.domain.validation.category;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


    /**
     * Checks if the String category exists.
     */
    @Documented
    @Constraint(validatedBy = {CategoryNotExistsValidator.class, MainCategoryNotExistsValidator.class})
    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    public @interface CategoryNotExists {
        String message() default "{jakarta.validation.constraints.CategoryNotExists.message}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
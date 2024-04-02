package com.example.gamezoneproject.domain.validation.other.notwhitespace;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotWhitespaceValidator implements ConstraintValidator<NotWhitespace, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containWhitespace = value.matches(".*\\s+.*");
        return !containWhitespace;
    }
}

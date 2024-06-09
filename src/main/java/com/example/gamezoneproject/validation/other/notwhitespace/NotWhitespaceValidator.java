package com.example.gamezoneproject.validation.other.notwhitespace;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NotWhitespaceValidator implements ConstraintValidator<NotWhitespace, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
         return !StringUtils.containsWhitespace(value);
    }
}

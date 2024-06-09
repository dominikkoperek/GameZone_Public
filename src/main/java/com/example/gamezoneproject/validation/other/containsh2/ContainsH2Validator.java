package com.example.gamezoneproject.validation.other.containsh2;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContainsH2Validator implements ConstraintValidator<ContainsH2, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return (s.contains("<h2>") && s.contains("</h2>"));
    }
}

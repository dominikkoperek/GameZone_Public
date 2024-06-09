package com.example.gamezoneproject.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < 8 || s.length() > 60) {
            return false;
        }
        char[] pass = s.toCharArray();
        boolean upperCase = false;
        boolean containsNumber = false;
        for (char c : pass) {
            if (Character.isUpperCase(c) && !upperCase) {
                upperCase = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            }
        }
        return upperCase && containsNumber;
    }
}

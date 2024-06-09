package com.example.gamezoneproject.validation.other.alphanumeric;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class AlphanumericValidator implements ConstraintValidator<Alphanumeric,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isAlphanumeric(s);
    }
}

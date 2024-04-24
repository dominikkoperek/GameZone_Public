package com.example.gamezoneproject.domain.validation.other.registrationAlphanumeric;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class AlphanumericAndSpecialSymbolsValidator implements ConstraintValidator<AlphanumericAndSpecialSymbols,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String s1 = s.replaceAll("-", "");
        String s2 = s1.replaceAll("_", "");
        return StringUtils.isAlphanumeric(s2);
    }
}

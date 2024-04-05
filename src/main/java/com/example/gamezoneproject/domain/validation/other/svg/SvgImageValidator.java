package com.example.gamezoneproject.domain.validation.other.svg;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SvgImageValidator implements ConstraintValidator<SvgImage, String> {

    @Override
    public boolean isValid(String svg, ConstraintValidatorContext constraintValidatorContext) {
        if (!svg.startsWith("<svg class=\"platform-logo\"")) {
            return false;
        }
        if(!svg.endsWith("</svg>")){
            return false;
        }
        return true;
    }
}

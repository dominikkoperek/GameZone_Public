package com.example.gamezoneproject.domain.validation.other.illegalexpression;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoIllegalExpressionValidator implements ConstraintValidator<NoIllegalExpression, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        if (s.contains("<h1>") || s.contains("<h3>") || s.contains("<h4>")
                || s.contains("<h5>") || s.contains("<h6>")) {
            return false;
        }
        return !s.contains("<script>");
    }
}

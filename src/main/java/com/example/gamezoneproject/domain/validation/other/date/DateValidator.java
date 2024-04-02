package com.example.gamezoneproject.domain.validation.other.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<Date, LocalDate> {
    private int min;
    private int max;

    @Override
    public void initialize(Date constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return true;
        }else {
        return localDate.getYear() >= min && localDate.getYear() <= max;
        }
    }
}

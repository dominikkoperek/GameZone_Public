package com.example.gamezoneproject.domain.validation.other.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Map;

public class DateValidator implements ConstraintValidator<Date, Map<String,LocalDate>> {
    private int min;
    private int max;

    @Override
    public void initialize(Date constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Map<String, LocalDate> stringLocalDateMap, ConstraintValidatorContext constraintValidatorContext) {
       return stringLocalDateMap
               .values()
               .stream()
               .anyMatch(v->v.getYear()>=min && v.getYear()<=max);
    }
}

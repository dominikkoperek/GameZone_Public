package com.example.gamezoneproject.domain.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldEqualField2Validator implements ConstraintValidator<FieldEqualField2, Object> {
    private String field1;
    private String field2;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object firstValue = new BeanWrapperImpl(value).getPropertyValue(field1);
        Object secondValue = new BeanWrapperImpl(value).getPropertyValue(field2);
        if (firstValue != null && secondValue !=field2) return firstValue.equals(secondValue);
        else return false;
    }

    @Override
    public void initialize(FieldEqualField2 constraintAnnotation) {
        this.field1 = constraintAnnotation.field1();
        this.field2 = constraintAnnotation.field2();
    }
}

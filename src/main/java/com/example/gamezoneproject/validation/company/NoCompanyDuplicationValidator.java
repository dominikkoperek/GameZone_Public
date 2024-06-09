package com.example.gamezoneproject.validation.company;

import com.example.gamezoneproject.domain.company.CompanyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoCompanyDuplicationValidator implements ConstraintValidator<NoCompanyDuplication, String> {
    private final CompanyService companyService;

    public NoCompanyDuplicationValidator(CompanyService companyService) {
        this.companyService = companyService;
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return companyService.isCompanyAvailable(value);
    }
}

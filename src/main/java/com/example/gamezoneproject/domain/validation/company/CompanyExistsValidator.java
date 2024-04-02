package com.example.gamezoneproject.domain.validation.company;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyExistsValidator implements ConstraintValidator<CompanyExists, String> {
    private final CompanyService companyService;

    public CompanyExistsValidator(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public boolean isValid(String company, ConstraintValidatorContext constraintValidatorContext) {
        if (company == null) {
            return false;
        }

        return !companyService.isCompanyAvailable(company);
    }
}

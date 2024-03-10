package com.example.gamezoneproject.domain.validation;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class NoCompanyDuplicationValidator implements ConstraintValidator<NoCompanyDuplication, String> {
    private final CompanyService companyService;

    public NoCompanyDuplicationValidator(CompanyService companyService) {
        this.companyService = companyService;
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<CompanyDto> companyDto = companyService.findCompanyByName(value);
        return companyDto.map(company -> !company.getName().equalsIgnoreCase(value)).orElse(true);
    }
}

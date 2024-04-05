package com.example.gamezoneproject.domain.validation.country;

import com.example.gamezoneproject.domain.game.gameDetails.country.CountryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryNotExistsValidator implements ConstraintValidator<CountryNotExists,String> {
    private final CountryService countryService;

    public CountryNotExistsValidator(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if(country == null){
            return false;
        }
        return !countryService.isCountryAvailable(country);
    }
}

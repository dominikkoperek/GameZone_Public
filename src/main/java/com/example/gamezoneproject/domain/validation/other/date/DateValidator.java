package com.example.gamezoneproject.domain.validation.other.date;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class DateValidator implements ConstraintValidator<Date, Map<String, LocalDate>> {
    private int min;
    private int max;
    private final GamePlatformRepository gamePlatformRepository;

    public DateValidator(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }

    @Override
    public void initialize(Date constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Map<String, LocalDate> stringLocalDateMap, ConstraintValidatorContext constraintValidatorContext) {
        for (String s : stringLocalDateMap.keySet()) {
            Optional<GamePlatform> byNameIgnoreCase = gamePlatformRepository.findByNameIgnoreCase(s);
            if (byNameIgnoreCase.isEmpty()) {
                return false;
            }
        }
        for (LocalDate value : stringLocalDateMap.values()) {
            if (value.getYear() < min || value.getYear() > max) {
                return false;
            }
        }
        return true;
    }
}

package com.example.gamezoneproject.domain.validation.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoPlatformDuplicationValidator implements ConstraintValidator<NoPlatformDuplication, String> {
    private final GamePlatformService gamePlatformService;

    public NoPlatformDuplicationValidator(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return gamePlatformService.isGamePlatformAvailable(s);
    }
}

package com.example.gamezoneproject.domain.validation.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;

public class PlatformExistsValidator implements ConstraintValidator<PlatformExists, Set<String>> {
    private final GamePlatformService gamePlatformService;

    public PlatformExistsValidator(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    @Override
    public boolean isValid(Set<String> platforms, ConstraintValidatorContext constraintValidatorContext) {
        if (platforms == null || platforms.isEmpty()) {
            return false;
        }
        for (String platform : platforms) {
            boolean platformAvailable = gamePlatformService.isGamePlatformAvailable(platform);
            if (platformAvailable) {
                return false;
            }
        }
        return true;
    }
}

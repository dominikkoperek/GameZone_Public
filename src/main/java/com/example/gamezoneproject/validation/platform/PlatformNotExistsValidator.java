package com.example.gamezoneproject.validation.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class PlatformNotExistsValidator implements ConstraintValidator<PlatformNotExists, Set<String>> {
    private final GamePlatformService gamePlatformService;

    public PlatformNotExistsValidator(GamePlatformService gamePlatformService) {
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

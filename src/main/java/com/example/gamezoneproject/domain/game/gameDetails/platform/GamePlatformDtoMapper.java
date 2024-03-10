package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;

public class GamePlatformDtoMapper {
    static GamePlatformDto map(GamePlatform gamePlatform) {
        return new GamePlatformDto(
                gamePlatform.getId(),
                gamePlatform.getName(),
                gamePlatform.getDescription(),
                gamePlatform.getLogoAddress()
        );
    }
}

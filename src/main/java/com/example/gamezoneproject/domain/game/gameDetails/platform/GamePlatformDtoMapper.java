package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.PlatformSuggestionsDto;

/**
 * Mapper class that maps from a GamePlatform entity to a GamePlatformDto.
 * It maps the id, name, description and logo address from the GamePlatform entity to the GamePlatformDto.
 */
public class GamePlatformDtoMapper {
    /**
     * This static method is responsible for mapping a GamePlatform entity to a GamePlatformDto.
     *
     * @param gamePlatform The GameMode object to be mapped.
     * @return A new GamePlatformDto object with fields mapped from the GamePlatform object.
     */
    static GamePlatformDto map(GamePlatform gamePlatform) {
        return new GamePlatformDto(
                gamePlatform.getId(),
                gamePlatform.getName(),
                gamePlatform.getDescription(),
                gamePlatform.getLogoAddress()
        );
    }
}


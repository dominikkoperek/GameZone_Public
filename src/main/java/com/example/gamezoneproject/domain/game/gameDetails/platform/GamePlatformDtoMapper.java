package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformBrandDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import org.springframework.stereotype.Service;

/**
 * Mapper class that maps from a GamePlatform entity to a GamePlatformDto.
 * It maps the id, name, description and logo address from the GamePlatform entity to the GamePlatformDto.
 */
@Service
public class GamePlatformDtoMapper {


    /**
     * This static method is responsible for mapping a GamePlatform entity to a GamePlatformDto.
     *
     * @param gamePlatform The GameMode object to be mapped.
     * @return A new GamePlatformDto object with fields mapped from the GamePlatform object.
     */
    public GamePlatformDto map(GamePlatform gamePlatform) {
        return new GamePlatformDto(
                gamePlatform.getId(),
                gamePlatform.getName(),
                gamePlatform.getDescription(),
                gamePlatform.getLogoAddress(),
                gamePlatform.getBrand()
        );
    }

    public GamePlatformBrandDto mapBrandDto(GamePlatform gamePlatform) {
        return new GamePlatformBrandDto(
                gamePlatform.getId(),
                gamePlatform.getName(),
                gamePlatform.getLogoAddressImage(),
                gamePlatform.getBrand()
        );
    }

}


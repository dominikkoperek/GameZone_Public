package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;

/**
 * Mapper class that maps from a GameMode entity to a GameModeDto.
 * It maps the name from the GameMode entity to the GameModeDto.
 */
public class GameModeDtoMapper {
    /**
     * This static method is responsible for mapping a GameMode entity to a GameModeDto.
     *
     * @param gamemode The GameMode object to be mapped.
     * @return A new GameModeDto object with fields mapped from the GameMode object.
     */
    public static GameModeDto map(GameMode gamemode) {
        return new GameModeDto(
                gamemode.getName()
        );
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;

public class GameModeDtoMapper {
    static GameModeDto map (GameMode gamemode){
        return new GameModeDto(
                gamemode.getName()
        );
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameGameModeDto;

public class GameGameModeDtoMapper {
    static GameGameModeDto map(GameGameMode gameMode){
        return new GameGameModeDto(
                gameMode.getGameMode(),
                gameMode.getGame(),
                gameMode.getDescription()
        );
    }
}

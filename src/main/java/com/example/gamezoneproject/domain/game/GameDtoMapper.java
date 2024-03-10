package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;

import java.util.stream.Collectors;

public class GameDtoMapper {
    static GameDto map(Game game) {
        return new GameDto(
                game.getId(),
                game.getTitle(),
                game.getDailymotionTrailerId(),
                game.getShortDescription(),
                game.getDescription(),
                game.getReleaseYear(),
                game.getCategory(),
                game.getGamePlatform().stream()
                        .collect(Collectors.toMap(GamePlatform::getName, GamePlatform::getLogoAddress)),
                game.getGameModes(),
                game.isPromoted(),
                game.getProducer(),
                game.getPublisher(),
                game.getPoster());
    }

    static GameByCompanyDto mapGameByCompanyId(Game game) {
        return new GameByCompanyDto(
                game.getId(),
                game.getTitle(),
                game.getReleaseYear()
        );
    }
}

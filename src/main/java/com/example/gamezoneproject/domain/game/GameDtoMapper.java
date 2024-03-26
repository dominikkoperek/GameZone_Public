package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;

import java.util.stream.Collectors;
/**
 * Mapper class that maps from a Game entity to a GameDto.
 */
public class GameDtoMapper {
    /**
     * This method maps a Game object to a GameDto object.
     * It takes a Game object as input and returns a new GameDto object with corresponding fields.
     *
     * @param game The Game object to be mapped.
     * @return A new GameDto object with fields mapped from the Game object.
     */
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
                game.getPoster(),
                game.getPlayerRange());
    }
    /**
     * This method maps a Game object to a GameByCompanyDto object.
     * It takes a Game object as input and returns a new GameByCompanyDto object with corresponding fields.
     *
     * @param game The Game object to be mapped.
     * @return A new GameByCompanyDto object with fields mapped from the Game object.
     */
    static GameByCompanyDto mapGameByCompanyId(Game game) {
        return new GameByCompanyDto(
                game.getId(),
                game.getTitle(),
                game.getReleaseYear(),
                game.getPoster()
        );
    }
}

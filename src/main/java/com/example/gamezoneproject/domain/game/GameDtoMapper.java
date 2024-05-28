package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.exceptions.GameNotFoundException;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.dto.GameSuggestionsDto;
import com.example.gamezoneproject.domain.game.dto.PromotedGameByCompanyDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendarRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper class that maps from a Game entity to a GameDto.
 */
@Service
public class GameDtoMapper {
    private final ReleaseCalendarRepository releaseDateRepository;

    public GameDtoMapper(ReleaseCalendarRepository releaseDateRepository) {
        this.releaseDateRepository = releaseDateRepository;
    }

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
                mapReleaseDateToMap(game),
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

    private static Map<String, LocalDate> mapReleaseDateToMap(Game game) {
        Map<String, LocalDate> releaseDateMap = game.getReleaseDate()
                .stream()
                .collect(Collectors.toMap(
                        ReleaseCalendar::getGamePlatform,
                        ReleaseCalendar::getReleaseDate,
                        (oldValue, newValue) -> oldValue
                ));
       return GameService.sortReleaseDates(releaseDateMap);
    }


    /**
     * This method maps a Game object to a GameByCompanyDto object.
     * It takes a Game object as input and returns a new GameByCompanyDto object with corresponding fields.
     *
     * @param game The Game object to be mapped.
     * @return A new GameByCompanyDto object with fields mapped from the Game object.
     */
    public GameByCompanyDto mapGameByCompanyId(Game game) {
        return new GameByCompanyDto(
                game.getId(),
                game.getTitle(),
                mapToFirstReleaseDate(game.getId()),
                game.getGamePlatform()
                        .stream()
                        .map(GamePlatform::getName)
                        .sorted(Comparator.comparing(String::trim)).toList(),
                game.getPoster()
        );
    }
    private LocalDate mapToFirstReleaseDate(Long gameId){
        return releaseDateRepository
                .findEarliestReleaseDateFromTodayByGameId(gameId).map(ReleaseCalendar::getReleaseDate)
                .orElse(LocalDate.of(9999, 1, 1));

    }

    /**
     * This method maps a Game object to a PromotedGameByCompanyDto object.
     *
     * @param game The Game object to be mapped.
     * @return A new PromotedGameByCompanyDto object with fields mapped from the Game object.
     */
    static PromotedGameByCompanyDto mapPromotedGameByCompanyId(Game game) {
        return new PromotedGameByCompanyDto(
                game.getId(),
                game.getTitle(),
                game.getPoster()
        );
    }

    /**
     * Method maps a Game object to GameSuggestionDto.
     * It takes title, release date, platforms, small and big poster suggestion and map them to DTO.
     *
     * @param game The Game object to be mapped.
     * @return A new GameSuggestionDto object with fields mapped from Game object.
     */
    GameSuggestionsDto mapToGameSuggestionsDto(Game game) {

        return new GameSuggestionsDto(
                game.getId(),
                game.getTitle(),
                mapReleaseDateToMap(game),
                game.getGamePlatform()
                        .stream()
                        .collect(Collectors.toMap(GamePlatform::getName, GamePlatform::getLogoAddressImage)),
                game.getSmallPosterSuggestion(),
                game.getBigPosterSuggestion(),
                getDaysBeforeRelease(game)
        );
    }

    private int getDaysBeforeRelease(Game game) {
        ReleaseCalendar earliestReleaseDate = releaseDateRepository
                .findEarliestReleaseDateFromTodayByGameId(game.getId())
                .orElseThrow(GameNotFoundException::new);
        return (int)ChronoUnit.DAYS.between(LocalDate.now(),earliestReleaseDate.getReleaseDate());
    }

}

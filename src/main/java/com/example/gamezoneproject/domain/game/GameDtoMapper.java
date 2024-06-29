package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.company.dto.CompanyApiDto;
import com.example.gamezoneproject.domain.company.dto.CompanyDto;
import com.example.gamezoneproject.domain.exceptions.GameNotFoundException;
import com.example.gamezoneproject.domain.game.dto.*;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendarRepository;
import com.example.gamezoneproject.domain.rating.Rating;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper class that maps from a Game entity to a GameDto.
 */
@Service
public class GameDtoMapper {
    private static final short MIN_VOTES_TO_CALCULATE_AVG_RATING = 2;
    private final ReleaseCalendarRepository releaseDateRepository;
    private final GameRepository gameRepository;

    public GameDtoMapper(ReleaseCalendarRepository releaseDateRepository, GameRepository gameRepository) {
        this.releaseDateRepository = releaseDateRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * This method maps a Game object to a GameDto object.
     * It takes a Game object as input and returns a new GameDto object with corresponding fields.
     *
     * @param game The Game object to be mapped.
     * @return A new GameDto object with fields mapped from the Game object.
     */
    static GameDto map(Game game) {
        int ratingCount = game.getRatings().size();
        double avgRating = 0;
        if (ratingCount >= MIN_VOTES_TO_CALCULATE_AVG_RATING) {
            avgRating = game.getRatings().stream()
                    .map(Rating::getRating)
                    .mapToDouble(val -> val)
                    .average().orElse(0);
        }


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
                game.getPlayerRange(),
                avgRating,
                ratingCount);
    }

    private static Map<String, LocalDate> mapReleaseDateToMap(Game game) {
        Map<String, LocalDate> releaseDateMap = game.getReleaseDate()
                .stream()
                .collect(Collectors.toMap(
                        gamePlatform -> gamePlatform.getGamePlatform().getName(),
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

    private LocalDate mapToFirstReleaseDate(Long gameId) {
        return releaseDateRepository
                .findFirstReleaseDate(gameId).map(ReleaseCalendar::getReleaseDate)
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
                mapPlatforms(game),
                game.getSmallPosterSuggestion(),
                game.getBigPosterSuggestion(),
                getDaysBeforeRelease(game)
        );
    }

    private Map<String, String> mapPlatforms(Game game) {
        return releaseDateRepository.findPlatformsByEarliestDateByGameId(game.getId())
                .stream()
                .collect(Collectors.toMap(GamePlatform::getName, GamePlatform::getLogoAddressImage));
    }

    private int getDaysBeforeRelease(Game game) {
        ReleaseCalendar earliestReleaseDate = releaseDateRepository
                .findLatestReleaseDateByGameId(game.getId())
                .orElseThrow(GameNotFoundException::new);
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), earliestReleaseDate.getReleaseDate());
    }
    /**
     * This static method is responsible for mapping a GameDto entity to a GameApiDto.
     *
     * @param gameDto The GameDto object to be mapped.
     * @return A new GameApiDto object with id and name fields mapped from the gameDto object.
     */
    public static GameApiDto mapToApiDto(GameDto gameDto){
        return new GameApiDto(
                gameDto.getId(),
                gameDto.getTitle()
        );
    }

}

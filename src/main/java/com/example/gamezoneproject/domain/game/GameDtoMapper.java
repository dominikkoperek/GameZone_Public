package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.exceptions.GameNotFoundException;
import com.example.gamezoneproject.domain.game.dto.*;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendarRepository;
import com.example.gamezoneproject.domain.rating.Rating;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper class that maps from a Game entity to a GameDto.
 */
@Service
public class GameDtoMapper {
    public static final short MIN_VOTES_TO_CALCULATE_AVG_RATING = 2;
    private static final DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.ENGLISH));
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
    public  GameDto map(Game game) {
        int ratingCount = game.getRatings().size();
        double avgRating = 0;
        if (ratingCount >= MIN_VOTES_TO_CALCULATE_AVG_RATING) {
            avgRating = game.getRatings().stream()
                    .map(Rating::getRating)
                    .mapToDouble(val -> val)
                    .average()
                    .orElse(0);
        }
        df.setRoundingMode(RoundingMode.UP);



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
                Double.parseDouble(df.format(avgRating)),
                ratingCount);
    }

    private  Map<String, LocalDate> mapReleaseDateToMap(Game game) {
        Map<String, LocalDate> releaseDateMap = game.getReleaseDate()
                .stream()
                .collect(Collectors.toMap(
                        gamePlatform -> gamePlatform.getGamePlatform().getName(),
                        ReleaseCalendar::getReleaseDate,
                        (oldValue, newValue) -> oldValue
                ));
        return sortReleaseDates(releaseDateMap);
    }
    /**
     * Method sorting game release dates map by value (date) descending
     *
     * @param releaseDateMap map with all release dates for game.
     * @return sorted map with all release dates for game.
     */
    private Map<String, LocalDate> sortReleaseDates(Map<String, LocalDate> releaseDateMap) {
        List<Map.Entry<String, LocalDate>> entryList = new ArrayList<>(releaseDateMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        Map<String, LocalDate> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, LocalDate> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
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
    public  PromotedGameByCompanyDto mapPromotedGameByCompanyId(Game game) {
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
    public GameSuggestionsDto mapToGameSuggestionsDto(Game game) {

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
     * @param game The GameDto object to be mapped.
     * @return A new GameApiDto object with id and name fields mapped from the gameDto object.
     */
    public GameApiDto mapToApiDto(Game game) {
        return new GameApiDto(
                game.getId(),
                game.getTitle()
        );
    }

}

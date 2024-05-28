package com.example.gamezoneproject.domain.game.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Simple DTO class for the Game entity.
 * This is used when for displaying basic information in suggestions fields in view.
 * Such as adds poster,title ,release date for counting and platforms and number of days before release.
 */
public class GameSuggestionsDto {
    private Long id;
    private String title;
    private Map<String, LocalDate> releaseYear;
    private Map<String, String> platform;
    private String smallPosterSuggestion;
    private String bigPosterSuggestion;
    private int daysBeforeRelease;

    public GameSuggestionsDto(Long id, String title, Map<String, LocalDate> releaseYear, Map<String,
            String> platform, String smallPosterSuggestion, String bigPosterSuggestion, int daysBeforeRelease) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.platform = platform;
        this.smallPosterSuggestion = smallPosterSuggestion;
        this.bigPosterSuggestion = bigPosterSuggestion;
        this.daysBeforeRelease = daysBeforeRelease;
    }

    public GameSuggestionsDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, LocalDate> getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Map<String, LocalDate> releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Map<String, String> getPlatform() {
        return new TreeMap<>(platform);
    }

    public void setPlatform(Map<String, String> platform) {
        this.platform = platform;
    }

    public String getSmallPosterSuggestion() {
        return smallPosterSuggestion;
    }

    public void setSmallPosterSuggestion(String smallPosterSuggestion) {
        this.smallPosterSuggestion = smallPosterSuggestion;
    }

    public String getBigPosterSuggestion() {
        return bigPosterSuggestion;
    }

    public void setBigPosterSuggestion(String bigPosterSuggestion) {
        this.bigPosterSuggestion = bigPosterSuggestion;
    }

    public int getDaysBeforeRelease() {
        return daysBeforeRelease;
    }

    public void setDaysBeforeRelease(int daysBeforeRelease) {
        this.daysBeforeRelease = daysBeforeRelease;
    }
}

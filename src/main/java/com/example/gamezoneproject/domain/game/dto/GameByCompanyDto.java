package com.example.gamezoneproject.domain.game.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Simple DTO class for the Game entity.
 * This is used when searching for games by a company's id, and includes information such as the
 * game's id, title, release date and poster.
 */
public class GameByCompanyDto {
    private Long id;
    private String title;
    private LocalDate firstReleaseDate;
    private List<String> platforms;
    private String poster;

    public GameByCompanyDto(Long id, String title, LocalDate firstReleaseDate, List<String> platforms, String poster) {
        this.id = id;
        this.title = title;
        this.firstReleaseDate = firstReleaseDate;
        this.platforms = platforms;
        this.poster = poster;
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

    public LocalDate getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(LocalDate firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }
}

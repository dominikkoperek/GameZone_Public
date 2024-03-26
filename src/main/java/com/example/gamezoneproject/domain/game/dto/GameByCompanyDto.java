package com.example.gamezoneproject.domain.game.dto;

import java.time.LocalDate;

/**
 * Simple DTO class for the Game entity.
 * This is used when searching for games by a company's id, and includes information such as the
 * game's id, title, release date and poster.
 */
public class GameByCompanyDto {
    private Long id;
    private String title;
    private LocalDate releaseYear;
    private String poster;

    public GameByCompanyDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public GameByCompanyDto(Long id, String title, LocalDate releaseYear, String poster) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
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

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

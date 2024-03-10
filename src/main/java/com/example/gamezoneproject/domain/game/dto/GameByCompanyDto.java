package com.example.gamezoneproject.domain.game.dto;

import java.time.LocalDate;

public class GameByCompanyDto {
    private Long id;
    private String title;
    private LocalDate releaseYear;

    public GameByCompanyDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public GameByCompanyDto(Long id, String title, LocalDate releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
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
}

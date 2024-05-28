package com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ReleaseCalendar {
    public ReleaseCalendar() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gamePlatform;
    private LocalDate releaseDate;

    public ReleaseCalendar(String gamePlatform, LocalDate releaseDate) {
        this.gamePlatform = gamePlatform;
        this.releaseDate = releaseDate;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

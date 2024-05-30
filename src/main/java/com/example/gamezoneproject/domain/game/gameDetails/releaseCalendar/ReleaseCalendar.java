package com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ReleaseCalendar {
    public ReleaseCalendar() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "Game_platform_id")
    private GamePlatform gamePlatform;
    private LocalDate releaseDate;

    public ReleaseCalendar(GamePlatform gamePlatform, LocalDate releaseDate) {
        this.gamePlatform = gamePlatform;
        this.releaseDate = releaseDate;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GamePlatform getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(GamePlatform gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

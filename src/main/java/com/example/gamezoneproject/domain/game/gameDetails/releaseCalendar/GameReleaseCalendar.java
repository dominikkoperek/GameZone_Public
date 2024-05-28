package com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar;

import com.example.gamezoneproject.domain.game.Game;
import jakarta.persistence.*;

@Entity
@Table(name = "game_release_calendar")
public class GameReleaseCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "release_calendar_id")
    private ReleaseCalendar releaseCalendar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameMode;
import jakarta.persistence.*;

@Entity
public class GameGameMode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_mode_id")
    private GameMode gameMode;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}


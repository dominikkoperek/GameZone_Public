package com.example.gamezoneproject.domain.game.gameDetails.modes.dto;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameMode;

public class GameGameModeDto {
    private Long id;

    private GameMode gameMode;
    private Game game;
    private String description;

    public GameGameModeDto(GameMode gameMode, Game game, String description) {
        this.gameMode = gameMode;
        this.game = game;
        this.description = description;
    }

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

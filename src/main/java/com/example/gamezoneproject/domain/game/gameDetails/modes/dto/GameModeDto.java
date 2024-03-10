package com.example.gamezoneproject.domain.game.gameDetails.modes.dto;

public class GameModeDto {
    public GameModeDto(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

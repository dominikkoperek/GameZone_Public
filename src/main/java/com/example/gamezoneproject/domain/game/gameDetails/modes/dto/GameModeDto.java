package com.example.gamezoneproject.domain.game.gameDetails.modes.dto;
/**
 *   Main DTO class for the GameMode.
 *   It's responsible for mapping from the GameMode entity to DTO.
 *   This DTO contain name field.
 */
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
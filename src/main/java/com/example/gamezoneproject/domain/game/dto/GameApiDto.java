package com.example.gamezoneproject.domain.game.dto;

public class GameApiDto {
    private Long id;
    private String title;

    public GameApiDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
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
}

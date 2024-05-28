package com.example.gamezoneproject.domain.game.dto;

import java.time.LocalDate;
import java.util.Map;

public class PromotedGameByCompanyDto {
    private Long id;
    private String title;
    private String poster;

    public PromotedGameByCompanyDto(Long id, String title, String poster) {
        this.id = id;
        this.title = title;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

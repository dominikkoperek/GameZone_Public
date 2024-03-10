package com.example.gamezoneproject.domain.game.gameDetails.country.dto;

public class CountryDto {
    private Long id;
    private String name;

    public CountryDto() {
    }

    public CountryDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.country.dto;
/**
 * Main DTO class for the Countries.
 * It's responsible for mapping from the Country entity to DTO.
 * This DTO contain ALL country details fields.
 */
public class CountryDto {
    private Long id;
    private String name;

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

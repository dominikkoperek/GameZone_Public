package com.example.gamezoneproject.domain.game.gameDetails.company.dto;

public class CompanyApiDto {
    private Long id;

    private String name;

    public CompanyApiDto(Long id, String name) {
        this.id = id;
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
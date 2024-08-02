package com.example.gamezoneproject.domain.game.gameDetails.platform.dto;

public class GamePlatformBrandDto {
    private Long id;
    private String name;
    private String image;
    private String brand;

    public GamePlatformBrandDto(Long id, String name, String image, String brand) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.brand = brand;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}

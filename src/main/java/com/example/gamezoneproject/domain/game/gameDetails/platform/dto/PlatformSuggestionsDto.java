package com.example.gamezoneproject.domain.game.gameDetails.platform.dto;

public class PlatformSuggestionsDto {
    private Long id;
    private String name;
    private String logoAddressImage;

    public PlatformSuggestionsDto(Long id, String name, String logoAddressImage) {
        this.id = id;
        this.name = name;
        this.logoAddressImage = logoAddressImage;
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

    public String getLogoAddressImage() {
        return logoAddressImage;
    }

    public void setLogoAddressImage(String logoAddressImage) {
        this.logoAddressImage = logoAddressImage;
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.platform.dto;

import com.example.gamezoneproject.validation.other.illegalexpression.NoIllegalExpression;
import com.example.gamezoneproject.validation.other.nohtmltags.NoHtmlTags;
import com.example.gamezoneproject.validation.other.svg.SvgImage;
import com.example.gamezoneproject.validation.platform.NoPlatformDuplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * Main DTO class for the GamePlatform.
 * It's responsible for mapping from the GamePlatform entity to DTO.
 * This DTO contain ALL game platform details fields.
 */
public class GamePlatformDto {
    private Long id;
    @Size(min = 2,max = 10)
    @NoIllegalExpression
    @NoPlatformDuplication
    @NoHtmlTags
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 50, max = 800)
    @NoHtmlTags
    private String description;
    @NotBlank
    @Size(min = 100,max = 30_000)
    @NoIllegalExpression
    @SvgImage
    private String logoAddress;
    private String brand;

    public GamePlatformDto(Long id, String name, String description, String logoAddress, String brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoAddress = logoAddress;
        this.brand = brand;
    }

    public GamePlatformDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoAddress() {
        return logoAddress;
    }

    public void setLogoAddress(String logoAddress) {
        this.logoAddress = logoAddress;
    }
}

package com.example.gamezoneproject.domain.game.gameDetails.category.dto;

import com.example.gamezoneproject.domain.validation.NoCategoryDuplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {
    private Long id;
    @NoCategoryDuplication
    @NotBlank
    @Size(min = 3,max = 20)
    private String name;
    @NotBlank
    @Size(min = 50,max = 800)
    private String description;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

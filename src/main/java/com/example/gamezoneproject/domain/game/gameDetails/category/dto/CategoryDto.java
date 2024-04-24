package com.example.gamezoneproject.domain.game.gameDetails.category.dto;

import com.example.gamezoneproject.domain.validation.category.NoCategoryDuplication;
import com.example.gamezoneproject.domain.validation.other.nohtmltags.NoHtmlTags;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Main DTO class for the Game categories.
 * It's responsible for mapping from the Categories entity to DTO.
 * This DTO contain ALL game details fields.
 */
public class CategoryDto {
    private Long id;
    @NoCategoryDuplication
    @NotBlank
    @NoHtmlTags
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank
    @Size(min = 50, max = 800)
    @NoHtmlTags
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

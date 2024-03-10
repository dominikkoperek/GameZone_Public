package com.example.gamezoneproject.domain.game.gameDetails.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;

public class CategoryDtoMapper {
    public static CategoryDto map (Category category){
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}

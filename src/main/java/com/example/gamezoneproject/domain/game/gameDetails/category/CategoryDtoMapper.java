package com.example.gamezoneproject.domain.game.gameDetails.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;

/**
 * Mapper class that maps from a Category entity to a CategoryDto.
 */
public class CategoryDtoMapper {
    /**
     * This static method is responsible for mapping a Category entity to a CategoryDto.
     *
     * @param category The Category object to be mapped.
     * @return A new CategoryDto object with fields mapped from the Category object.
     */
     static CategoryDto map(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}

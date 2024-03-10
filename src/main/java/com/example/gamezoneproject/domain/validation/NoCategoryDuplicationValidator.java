package com.example.gamezoneproject.domain.validation;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class NoCategoryDuplicationValidator implements ConstraintValidator<NoCategoryDuplication, String> {
    private final CategoryService categoryService;

    public NoCategoryDuplicationValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<CategoryDto> categoryOptional = categoryService.findCategoryByName(value);
        return categoryOptional.map(category -> !category.getName().equalsIgnoreCase(value)).orElse(true);
    }
}

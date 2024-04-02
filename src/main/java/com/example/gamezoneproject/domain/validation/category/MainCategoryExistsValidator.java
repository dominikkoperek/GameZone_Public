package com.example.gamezoneproject.domain.validation.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MainCategoryExistsValidator implements ConstraintValidator<CategoryExists, String> {
    private final CategoryService categoryService;

    public MainCategoryExistsValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
        if (category == null) {
            return false;
        }
        boolean categoryAvailable = categoryService.isCategoryAvailable(category);
        return !categoryAvailable;
    }
}

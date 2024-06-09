package com.example.gamezoneproject.validation.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MainCategoryNotExistsValidator implements ConstraintValidator<CategoryNotExists, String> {
    private final CategoryService categoryService;

    public MainCategoryNotExistsValidator(CategoryService categoryService) {
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

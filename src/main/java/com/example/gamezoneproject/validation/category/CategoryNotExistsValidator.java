package com.example.gamezoneproject.validation.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.LinkedList;

public class CategoryNotExistsValidator implements ConstraintValidator<CategoryNotExists, LinkedList<String>> {
    private final CategoryService categoryService;

    public CategoryNotExistsValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(LinkedList<String> categories, ConstraintValidatorContext constraintValidatorContext) {
        if (categories == null || categories.isEmpty()) {
            return false;
        }
        for (String category : categories) {
            boolean categoryAvailable = categoryService.isCategoryAvailable(category);
            if (categoryAvailable) {
                return false;
            }

        }
        return true;
    }
}

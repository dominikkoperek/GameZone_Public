package com.example.gamezoneproject.domain.validation;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotWhitespaceValidator implements ConstraintValidator<NotWhitespace, String> {
    private CategoryService categoryService;

    public NotWhitespaceValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containWhitespace = value.matches(".*\\s+.*");
        return !containWhitespace;
    }
}

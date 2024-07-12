package com.example.gamezoneproject.validation.game;

import com.example.gamezoneproject.domain.game.service.GameService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoGameDuplicationValidator implements ConstraintValidator<NoGameDuplication,String> {
    private final GameService gameService;

    public NoGameDuplicationValidator(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return  gameService.isTitleAvailable(s);
    }
}

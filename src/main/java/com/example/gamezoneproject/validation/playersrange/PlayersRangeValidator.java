package com.example.gamezoneproject.validation.playersrange;

import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlayersRangeValidator implements ConstraintValidator<PlayersRange, PlayerRange> {
    private int rangeMin;
    private int rangeMax;
    private int maxMin;

    @Override
    public void initialize(PlayersRange constraintAnnotation) {
        rangeMin = constraintAnnotation.rangeMin();
        rangeMax = constraintAnnotation.rangeMax();
        maxMin = constraintAnnotation.maxMin();
    }

    @Override
    public boolean isValid(PlayerRange playerRange, ConstraintValidatorContext constraintValidatorContext) {
        if (playerRange.getMinPlayers() > playerRange.getMaxPlayers()) {
            return false;
        }
        if (playerRange.getMinPlayers() < rangeMin) {
            return false;
        }
        if (playerRange.getMinPlayers() > maxMin) {
            return false;
        }
        if (playerRange.getMaxPlayers() > rangeMax) {
            return false;
        }

        return true;
    }
}

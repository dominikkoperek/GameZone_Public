package com.example.gamezoneproject.domain.game.gameDetails.country;

import com.example.gamezoneproject.domain.game.gameDetails.country.dto.CountryDto;

public class CountryDtoMapper {
    public static CountryDto map(Country country) {
        return new CountryDto(
                country.getName()
        );
    }
}

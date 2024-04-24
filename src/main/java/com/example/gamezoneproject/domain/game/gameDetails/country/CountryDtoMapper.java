package com.example.gamezoneproject.domain.game.gameDetails.country;

import com.example.gamezoneproject.domain.game.gameDetails.country.dto.CountryDto;

/**
 * Mapper class that maps from a Country entity to a CategoryDto.
 */
public class CountryDtoMapper {
    /**
     * This static method is responsible for mapping a Country entity to a CountryDto.
     *
     * @param country The Country object to be mapped.
     * @return A new CountryDto object with fields mapped from the Country object.
     */
     static CountryDto map(Country country) {
        return new CountryDto(
                country.getName()
        );
    }
}

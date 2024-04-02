package com.example.gamezoneproject.domain.game.gameDetails.company;

import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;

/**
 * Mapper class that maps from a Company entity to a CompanyDto.
 */
public class CompanyDtoMapper {
    /**
     * This static method is responsible for mapping a Company entity to a CompanyDto.
     *
     * @param company The Company object to be mapped.
     * @return A new CompanyDto object with fields mapped from the Company object.
     */
    public static CompanyDto map(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getShortDescription(),
                company.getCountry(),
                company.getProducer(),
                company.getPublisher(),
                company.getDescription(),
                company.getPoster()
        );
    }

}

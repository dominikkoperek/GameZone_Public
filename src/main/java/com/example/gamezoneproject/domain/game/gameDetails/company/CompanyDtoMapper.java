package com.example.gamezoneproject.domain.game.gameDetails.company;

import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyApiDto;
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
     static CompanyDto map(Company company) {
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
    /**
     * This static method is responsible for mapping a CompanyDto entity to a CompanyApiDto.
     *
     * @param companyDto The CompanyDto object to be mapped.
     * @return A new CompanyApiDto object with fields mapped from the companyDto object.
     */
     public static CompanyApiDto mapToApiDto(CompanyDto companyDto){
        return new CompanyApiDto(
                companyDto.getId(),
                companyDto.getName()
        );
    }

}

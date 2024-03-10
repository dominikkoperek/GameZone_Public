package com.example.gamezoneproject.domain.game.gameDetails.company;

import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;

public class CompanyDtoMapper {
   public static CompanyDto map(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getShortDescription(),
                company.getCountry(),
                company.getProducer(),
                company.getPublisher(),
                company.getDescription()
        );
    }
}

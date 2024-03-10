package com.example.gamezoneproject.domain.game.gameDetails.country;

import com.example.gamezoneproject.domain.game.gameDetails.country.dto.CountryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<String> findAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false)
                .map(CountryDtoMapper::map)
                .map(CountryDto::getName)
                .toList();
    }
}

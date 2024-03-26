package com.example.gamezoneproject.domain.game.gameDetails.country;

import com.example.gamezoneproject.domain.game.gameDetails.country.dto.CountryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;
/**
 * This class shares public methods which allow to search countries.
 * It uses the CountryRepository to interact with the database.
 */
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * This method uses the repository to find all countries in the database.
     * It maps the result to a CompanyDto and them map their names.
     *
     * @return A list of all countries names.
     */
    public List<String> findAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false)
                .map(CountryDtoMapper::map)
                .map(CountryDto::getName)
                .toList();
    }
}

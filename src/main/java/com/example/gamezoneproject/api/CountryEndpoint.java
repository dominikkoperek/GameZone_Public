package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.country.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint controller responsible for getting info about countries from database
 */
@RestController
@RequestMapping("/api/country")
public class CountryEndpoint {
    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Endpoint responsible for finding and returning all available countries names from database
     *
     * @return ResponseEntity that hold list of countries names
     */

    @GetMapping("/allCountries")
    public ResponseEntity<List<String>> findAllCountries() {
        List<String> allCountries = countryService.findAllCountries();
        return ResponseEntity.ok(allCountries);
    }
}

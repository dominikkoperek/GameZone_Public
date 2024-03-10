package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.country.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryEndpoint {
    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/allCountries")
    public ResponseEntity<?> findAllCountries(){
        List<String> allCountries = countryService.findAllCountries();
        return ResponseEntity.ok(allCountries);
    }
}

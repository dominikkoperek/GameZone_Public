package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyDtoMapper;
import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyApiDto;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint controller responsible for getting info about companies from database.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyEndpoint {
    private final CompanyService companyService;

    public CompanyEndpoint(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Endpoint that check if company already exists in database by provided name.
     * If the name param is null, it returns all companies.
     *
     * @param name name of the company provided by user (optional).
     * @return ResponseEntity containing true/false if the name is provided, or ResponseEntity containing a list.
     * of all companies if the name is null.
     */
    @GetMapping("/availability")
    public ResponseEntity<?> checkCompanyAvailability(@RequestParam(required = false) String name) {
        boolean isCompanyAvailable = companyService.isCompanyAvailable(name);
        if (name == null) {
            return findAllCompanies();
        }
        return ResponseEntity.ok(isCompanyAvailable);
    }

    /**
     * Endpoint that find all companies in database and map them to get their names.
     *
     * @return ResponseEntity containing a list of all companies names.
     */
    @GetMapping("/allCompanies")
    public ResponseEntity<List<CompanyApiDto>> findAllCompanies() {
        List<CompanyApiDto> allCompanies = companyService
                .findAllCompanies()
                .stream()
                .map(CompanyDtoMapper::mapToApiDto)
                .toList();
        return ResponseEntity.ok(allCompanies);
    }


    /**
     * Endpoint that finds all companies in database that are producers and maps them to get their names.
     *
     * @return ResponseEntity containing a list of all company names that are producers.
     */
    @GetMapping("/allProducers")
    public ResponseEntity<List<String>> findAllProducers() {
        List<String> allProducers = companyService
                .findAllProducers()
                .stream()
                .map(CompanyDto::getName)
                .toList();
        return ResponseEntity.ok(allProducers);
    }

    /**
     * Endpoint that finds all companies in database that are publishers and map them to get their names.
     *
     * @return ResponseEntity containing a list of all company names that are producers.
     */
    @GetMapping("/allPublishers")
    public ResponseEntity<List<String>> findAllPublishers() {
        List<String> allProducers = companyService
                .findAllPublishers()
                .stream()
                .map(CompanyDto::getName)
                .toList();
        return ResponseEntity.ok(allProducers);
    }

}

package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyEndpoint {
    private final CompanyService companyService;

    public CompanyEndpoint(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/availability")
    public ResponseEntity<?> checkCompanyAvailability(@RequestParam(required = false) String company) {
        boolean isCompanyAvailable = companyService.isCompanyAvailable(company);
        if (company == null) {
            return findAllCompanies();
        }
        return ResponseEntity.ok(isCompanyAvailable);
    }

    @GetMapping("/allCompanies")
    public ResponseEntity<?> findAllCompanies() {
        List<String> allCompanies = companyService
                .findAllCompanies()
                .stream()
                .map(CompanyDto::getName)
                .toList();
        return ResponseEntity.ok(allCompanies);
    }

    @GetMapping("/allProducers")
    public ResponseEntity<?> findAllProducers() {
        List<String> allProducers = companyService
                .findAllProducers()
                .stream()
                .map(CompanyDto::getName)
                .toList();
        return ResponseEntity.ok(allProducers);
    }

    @GetMapping("/allPublishers")
    public ResponseEntity<?> findAllPublishers() {
        List<String> allProducers = companyService
                .findAllPublishers()
                .stream()
                .map(CompanyDto::getName)
                .toList();
        return ResponseEntity.ok(allProducers);
    }

}

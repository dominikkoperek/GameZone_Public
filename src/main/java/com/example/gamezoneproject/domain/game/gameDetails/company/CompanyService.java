package com.example.gamezoneproject.domain.game.gameDetails.company;

import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CompanyService {
    private final static String POLISH_COUNTRY_NAME ="Polska";
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<CompanyDto> findCompanyByName(String name) {
        return companyRepository.findByNameIgnoreCase(name)
                .map(CompanyDtoMapper::map);
    }

    public List<CompanyDto> findAllCompanies() {
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(CompanyDtoMapper::map)
                .toList();
    }

    public List<CompanyDto> findAllPolishProducers() {
     return  companyRepository.findAllByIsProducerIsTrueAndCountry(POLISH_COUNTRY_NAME)
             .stream()
             .map(CompanyDtoMapper::map)
             .toList();
    }
    public List<CompanyDto> findAllPolishPublishers() {
        return  companyRepository.findAllByIsPublisherIsTrueAndCountry(POLISH_COUNTRY_NAME)
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }
    public List<CompanyDto> findAllPublishers() {
        return  companyRepository.findAllByIsPublisherIsTrue()
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }
    public List<CompanyDto> findAllProducers() {
        return  companyRepository.findAllByIsProducerIsTrue()
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    @Transactional
    public void addCompany(CompanyDto companyDto) {
        Company companyToSave = new Company();
        companyToSave.setName(companyDto.getName());
        companyToSave.setShortDescription(companyDto.getShortDescription());
        companyToSave.setDescription(companyDto.getDescription());
        companyToSave.setCountry(companyDto.getCountry());
        companyToSave.setProducer(companyDto.getProducer());
        companyToSave.setPublisher(companyDto.getPublisher());
        companyRepository.save(companyToSave);
    }

    public boolean isCompanyAvailable(String companyName) {
        return companyRepository.findByNameIgnoreCase(companyName).isEmpty();
    }

    public Optional<CompanyDto> findById(Long id) {
        return companyRepository.findById(id).map(CompanyDtoMapper::map);
    }

}

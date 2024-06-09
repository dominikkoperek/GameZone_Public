package com.example.gamezoneproject.domain.company;

import com.example.gamezoneproject.domain.company.dto.CompanyDto;
import com.example.gamezoneproject.domain.company.dto.CompanySaveDto;
import com.example.gamezoneproject.storage.FileStorageService;
import com.example.gamezoneproject.storage.storageStrategy.CompanyPoster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * This class shares public methods which allow to search, add, and check availability of companies.
 * It uses the CompaniesRepository to interact with the database.
 */
@Service
public class CompanyService {
    private final static String POLISH_COUNTRY_NAME = "Polska";
    private final CompanyRepository companyRepository;
    private final FileStorageService fileStorageService;
    private final CompanyPoster companyPoster;

    public CompanyService(CompanyRepository companyRepository, FileStorageService fileStorageService, CompanyPoster companyPoster) {
        this.companyRepository = companyRepository;
        this.fileStorageService = fileStorageService;
        this.companyPoster = companyPoster;
    }

    /**
     * This method uses the repository to find company by name param.
     * It maps the result to a CompanyDto
     *
     * @param name name of the company.
     * @return Optional containing a CompanyDto if the name exists, or empty optional.
     */
    public Optional<CompanyDto> findCompanyByName(String name) {
        return companyRepository.findByNameIgnoreCase(name)
                .map(CompanyDtoMapper::map);
    }

    /**
     * This method uses the repository to find all companies in the database.
     * It maps the result to a CompanyDto
     *
     * @return A list of all companies mapped to CompanyDto.
     */
    public List<CompanyDto> findAllCompanies() {
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * This method uses the repository to find all polish companies that are producers in the database.
     * It maps the result to a CompanyDto
     *
     * @return A list of all polish companies that are producers, mapped to CompanyDto.
     */
    public List<CompanyDto> findAllPolishProducers() {
        return companyRepository.findAllByIsProducerIsTrueAndCountry(POLISH_COUNTRY_NAME)
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * This method uses the repository to find all polish companies that are publishers in the database.
     * It maps the result to a CompanyDto
     *
     * @return A list of all polish companies that are publishers, mapped to CompanyDto.
     */
    public List<CompanyDto> findAllPolishPublishers() {
        return companyRepository.findAllByIsPublisherIsTrueAndCountry(POLISH_COUNTRY_NAME)
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * This method uses the repository to find all companies that are publishers in the database. It maps the result to a CompanyDto.
     *
     * @return A list of all companies that are publishers, mapped to CompanyDto.
     */
    public List<CompanyDto> findAllPublishers() {
        return companyRepository.findAllByIsPublisherIsTrue()
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * This method uses the repository to find all companies that are producers in the database. It maps the result to a CompanyDto.
     *
     * @return A list of all companies that are producers, mapped to CompanyDto.
     */
    public List<CompanyDto> findAllProducers() {
        return companyRepository.findAllByIsProducerIsTrue()
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * This method uses the repository to save a new company in the database. It uses a CompanyDto to get all the necessary information.
     *
     * @param companySaveDto An CompanySaveDto object.
     */
    @Transactional
    public void addCompany(CompanySaveDto companySaveDto) {
        Company companyToSave = new Company();
        companyToSave.setName(companySaveDto.getName());
        companyToSave.setShortDescription(companySaveDto.getShortDescription());
        companyToSave.setDescription(companySaveDto.getDescription());
        companyToSave.setCountry(companySaveDto.getCountry());
        companyToSave.setProducer(companySaveDto.isProducer());
        companyToSave.setPublisher(companySaveDto.isPublisher());
        if (companySaveDto.getPoster() != null && !companySaveDto.getPoster().isEmpty()) {
            String savedFileName = fileStorageService
                    .saveImage(companySaveDto.getPoster(), companySaveDto.getName(), companyPoster);
            companyToSave.setPoster(savedFileName);
        }

        companyRepository.save(companyToSave);
    }

    /**
     * This method uses the repository to check if a company with the provided name already exists in the database.
     *
     * @param companyName The name of the company.
     * @return True if the provided name does not exist in the database, and false if the company already exists in the database.
     */
    public boolean isCompanyAvailable(String companyName) {
        return companyRepository.findByNameIgnoreCase(companyName)
                .isEmpty();
    }

    /**
     * This method uses the repository to find a company by id in the database, and maps the result to a CompanyDto.
     *
     * @param id The id of the company.
     * @return An Optional containing a CompanyDto if a company with the provided id is found, or an empty Optional if no company is found.
     */
    public Optional<CompanyDto> findById(Long id) {
        return companyRepository
                .findById(id)
                .map(CompanyDtoMapper::map);
    }

    /**
     * Method uses the repository to find all companies by the first letter of their name.
     *
     * @param letter First letter of the company name.
     * @return List of CompanyDto filled with companies that first letter is param letter.
     */
    public List<CompanyDto> findAllCompaniesByFirstLetter(String letter) {
        return companyRepository.findAllByNameStartingWith(letter)
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();
    }

    /**
     * Method uses the repository to find all companies where first letter of the name is digit.
     *
     * @return List of company dto with companies that names starts with digit.
     */
    public List<CompanyDto> findAllCompaniesByNameStartWithDigit() {
        return companyRepository.findAllByNameStartingWithDigit()
                .stream()
                .map(CompanyDtoMapper::map)
                .toList();

    }

}
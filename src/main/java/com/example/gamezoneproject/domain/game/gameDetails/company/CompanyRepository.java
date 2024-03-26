package com.example.gamezoneproject.domain.game.gameDetails.company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Company entity in database.
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {
    /**
     * Finds a company by name, ignoring case.
     *
     * @param name The name of the company.
     * @return An Optional containing the Company entity if found, or empty if not.
     */
    Optional<Company> findByNameIgnoreCase(String name);

    /**
     * Finds all companies that are producer and by country.
     *
     * @param country The name of the country.
     * @return A list of companies that are producers and are from the provided country.
     */
    List<Company> findAllByIsProducerIsTrueAndCountry(String country);

    /**
     * Finds all companies that are publishers and are from the provided country.
     *
     * @param country The name of the country.
     * @return A list of companies that are publishers and are from the provided country.
     */
    List<Company> findAllByIsPublisherIsTrueAndCountry(String country);

    /**
     * Finds all companies that are publishers.
     *
     * @return A list of all companies that are publishers.
     */
    List<Company> findAllByIsPublisherIsTrue();
    /**
     * Finds all companies that are producers.
     *
     * @return A list of all companies that are producers.
     */
    List<Company> findAllByIsProducerIsTrue();
}

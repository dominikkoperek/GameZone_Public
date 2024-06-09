package com.example.gamezoneproject.domain.game.gameDetails.country;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Country entity in database.
 */
public interface CountryRepository extends CrudRepository<Country,Long> {
    /**
     * Finds a country by name, ignoring case.
     *
     * @param name The name of the country.
     * @return An Optional containing the country entity if found, or empty if not.
     */
    Optional<Country> findByNameIgnoreCase(String name);
}

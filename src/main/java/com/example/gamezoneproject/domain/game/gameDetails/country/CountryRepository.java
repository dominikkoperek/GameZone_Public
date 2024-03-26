package com.example.gamezoneproject.domain.game.gameDetails.country;

import org.springframework.data.repository.CrudRepository;
/**
 * Hibernate Interface that is responsible for all operations with Country entity in database.
 */
public interface CountryRepository extends CrudRepository<Country,Long> {
}

package com.example.gamezoneproject.domain.game.gameDetails.company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findByNameIgnoreCase(String name);
    List<Company> findAllByIsProducerIsTrueAndCountry(String country);
    List<Company> findAllByIsPublisherIsTrueAndCountry(String country);
    List<Company> findAllByIsPublisherIsTrue();
    List<Company> findAllByIsProducerIsTrue();
}

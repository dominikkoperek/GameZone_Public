package com.example.gamezoneproject.domain.game.gameDetails.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Category entity in database.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    /**
     * Finds a category by name, ignoring case.
     *
     * @param name The category name.
     * @return An optional containing the category if found, or empty if not.
     */
    Optional<Category> findByNameIgnoreCase(String name);
}

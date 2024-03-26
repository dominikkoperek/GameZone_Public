package com.example.gamezoneproject.domain.game.gameDetails.platform;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with GamePlatform entity in database.
 */
public interface GamePlatformRepository extends CrudRepository<GamePlatform,Long> {
    /**
     * Finds the GamePlatform by the included param name, ignoring case.
     * @param name game platform name.
     * @return Optional containing the GamePlatform if found, or empty if not.
     */
    Optional<GamePlatform> findByNameIgnoreCase(String name);

}

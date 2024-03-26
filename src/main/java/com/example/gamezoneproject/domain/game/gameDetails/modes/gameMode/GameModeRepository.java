package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with GameMode entity in database.
 */
public interface GameModeRepository extends CrudRepository<GameMode, Long> {
    /**
     * Find by name, ignoring case.
     *
     * @param name The name of the game mode.
     * @return Optional of the entity GameMode if the name exists or empty.
     */
    Optional<GameMode> findByNameIgnoreCase(String name);
}

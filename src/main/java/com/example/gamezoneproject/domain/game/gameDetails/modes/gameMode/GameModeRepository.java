package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameModeRepository extends CrudRepository<GameMode,Long> {
    Optional<GameMode> findByNameIgnoreCase(String name);
}

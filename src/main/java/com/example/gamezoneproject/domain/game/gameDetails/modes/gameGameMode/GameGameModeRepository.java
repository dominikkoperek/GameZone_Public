package com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameGameModeRepository extends CrudRepository<GameGameMode,Long> {
    Optional<GameGameMode> findByGameMode_NameIgnoreCase(String name);
}

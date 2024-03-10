package com.example.gamezoneproject.domain.game.gameDetails.platform;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GamePlatformRepository extends CrudRepository<GamePlatform,Long> {
    Optional<GamePlatform> findByNameIgnoreCase(String gamePlatform);

}

package com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameGameModeDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameGameModeService {
    private final GameGameModeRepository gameGameModeRepository;

    public GameGameModeService(GameGameModeRepository gameGameModeRepository) {
        this.gameGameModeRepository = gameGameModeRepository;
    }

    public Optional<GameGameModeDto> findGameModeByName(String name) {
        return gameGameModeRepository.findByGameMode_NameIgnoreCase(name)
                .map(GameGameModeDtoMapper::map);
    }
}

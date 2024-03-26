package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * This class shares public methods which allow to search for game modes.
 * It uses the GameModeRepository to interact with the database.
 */
@Service
public class GameModeService {
    private final GameModeRepository gameModeRepository;

    public GameModeService(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }

    /**
     * This method uses the repository to find game mode by name param.
     * It maps the result to a GameModeDto
     *
     * @param name name of the game mode.
     * @return Optional containing a GameModeDto if the name exists, or empty optional.
     */
    public Optional<GameModeDto> findGameModeByName(String name) {
        return gameModeRepository
                .findByNameIgnoreCase(name)
                .map(GameModeDtoMapper::map);
    }
    /**
     * This method uses the repository to find all game modes in the database.
     * It maps the result to a GameModeDto
     *
     * @return A list of all game modes mapped to GameModeDto.
     */
    public List<GameModeDto> findAllGameModes() {
        return StreamSupport.stream(gameModeRepository.findAll().spliterator(), false)
                .map(GameModeDtoMapper::map)
                .toList();
    }
}

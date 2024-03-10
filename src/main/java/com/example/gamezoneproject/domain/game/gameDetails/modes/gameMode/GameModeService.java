package com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GameModeService {
    private final GameModeRepository gameModeRepository;

    public GameModeService(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }
    public Optional<GameModeDto> findGameModeByName(String name){
        return gameModeRepository.findByNameIgnoreCase(name).map(GameModeDtoMapper::map);
    }
    public List<GameModeDto> findAllGameModes(){
        return StreamSupport.stream(gameModeRepository.findAll().spliterator(),false)
                .map(GameModeDtoMapper::map).toList();
    }
}

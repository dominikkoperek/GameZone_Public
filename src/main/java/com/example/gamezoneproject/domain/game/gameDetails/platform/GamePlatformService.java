package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GamePlatformService {
    private final GamePlatformRepository gamePlatformRepository;

    public GamePlatformService(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }

    public Optional<GamePlatformDto> findGamePlatformByName(String name) {
        return gamePlatformRepository.findByNameIgnoreCase(name)
                .map(GamePlatformDtoMapper::map);
    }

    public LinkedHashMap<String, String> findAllGamePlatforms() {
        return StreamSupport.stream(gamePlatformRepository.findAll().spliterator(), false)
                .map(GamePlatformDtoMapper::map)
                .collect(Collectors.toMap(
                        GamePlatformDto::getName,
                        GamePlatformDto::getLogoAddress,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    @Transactional
    public void addGamePlatform(GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatformToSave = new GamePlatform();
        String nameCapitalize = StringUtils.capitalize(gamePlatformDto.getName());
        gamePlatformToSave.setName(nameCapitalize.trim());
        gamePlatformToSave.setDescription(gamePlatformDto.getDescription().trim());
        gamePlatformToSave.setLogoAddress(gamePlatformDto.getLogoAddress().trim());
        gamePlatformRepository.save(gamePlatformToSave);
    }
    public boolean isGamePlatformAvailable(String gamePlatformName){
        return gamePlatformRepository.findByNameIgnoreCase(gamePlatformName).isEmpty();

    }
}

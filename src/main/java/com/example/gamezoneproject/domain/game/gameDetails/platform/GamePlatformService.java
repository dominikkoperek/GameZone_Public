package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class shares public methods which allow to search, add, and check availability of game platforms.
 * It uses the GamePlatformRepository to interact with the database.
 */
@Service
public class GamePlatformService {
    private final static String ALL_CATEGORIES_NAME = "wszystkie";
    private final GamePlatformRepository gamePlatformRepository;

    public GamePlatformService(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }

    /**
     * This method uses the repository to find game platform by name param.
     * It maps the result to a GamePlatformDto.
     *
     * @param name name of the game platform.
     * @return Optional containing a GamePlatformDto if the name exists, or empty optional.
     */
    public Optional<GamePlatformDto> findGamePlatformByName(String name) {
        return gamePlatformRepository.findByNameIgnoreCase(name)
                .map(GamePlatformDtoMapper::map);
    }

    /**
     * This method uses the repository to find all game platforms in the database.
     * It maps the result to a GamePlatformDto, and collects them into a Map where the key is the platform name and
     * the value is the logo address.
     * If the map contains the same key, the old value is kept and the new one is ignored.
     *
     * @return A LinkedHashMap of all game platforms mapped.
     */
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

    /**
     * This method uses the repository to find all game platforms in the database.
     * It maps the result to names of them.
     *
     * @return A list of all game platforms names.
     */
    public List<String> findAllGamePlatformsNames() {
        return StreamSupport.stream(gamePlatformRepository.findAll().spliterator(), false)
                .map(GamePlatform::getName)
                .toList();
    }

    /**
     * This method uses the repository to find all game platforms without value "ALL_CATEGORIES_NAME" in the database.
     * It maps the result to names of them.
     *
     * @return A list of all game platforms names without value "ALL_CATEGORIES_NAME".
     */
    public List<String> findAllAvailableGamePlatformsNames() {
        return StreamSupport.stream(gamePlatformRepository.findAll().spliterator(), false)
                .map(GamePlatform::getName)
                .filter(name -> !name.equalsIgnoreCase(ALL_CATEGORIES_NAME))
                .toList();
    }

    /**
     * This method uses the repository to save a new game platform in the database.
     * It uses a GamePlatformDto to get all the necessary information.
     *
     * @param gamePlatformDto An gamePlatformDto containing the capitalize name, description and logo address.
     */
    @Transactional
    public void addGamePlatform(GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatformToSave = new GamePlatform();
        String nameCapitalize = StringUtils.capitalize(gamePlatformDto.getName());
        gamePlatformToSave.setName(nameCapitalize.trim());
        gamePlatformToSave.setDescription(gamePlatformDto.getDescription().trim());
        gamePlatformToSave.setLogoAddress(gamePlatformDto.getLogoAddress().trim());
        gamePlatformRepository.save(gamePlatformToSave);
    }

    /**
     * This method uses the repository to check if a game platform with the provided name already exists in the database.
     *
     * @param gamePlatformName The name of the game platform.
     * @return True if the provided name does not exist in the database, and false if the game platform already exists in the database.
     */
    public boolean isGamePlatformAvailable(String gamePlatformName) {
        return gamePlatformRepository.findByNameIgnoreCase(gamePlatformName).isEmpty();

    }
}

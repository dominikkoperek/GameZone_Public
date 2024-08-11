package com.example.gamezoneproject.domain.game.gameDetails.platform;

import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformBrandDto;
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
    private final GamePlatformDtoMapper gamePlatformDtoMapper;

    public GamePlatformService(GamePlatformRepository gamePlatformRepository, GamePlatformDtoMapper gamePlatformDtoMapper) {
        this.gamePlatformRepository = gamePlatformRepository;
        this.gamePlatformDtoMapper = gamePlatformDtoMapper;
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
                .map(gamePlatformDtoMapper::map);
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
                .map(gamePlatformDtoMapper::map)
                .sorted(Comparator.comparing(GamePlatformDto::getBrand))
                .collect(Collectors.toMap(
                        GamePlatformDto::getName,
                        GamePlatformDto::getLogoAddress,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    /**
     * This method retrieves all game platforms from the database, maps them to GamePlatformBrandDto objects,
     * sorts them by brand, and filters out the ones with the name ALL_CATEGORIES_NAME.
     *
     * @return A list of GamePlatformBrandDto objects representing all game platforms, excluding those named ALL_CATEGORIES_NAME.
     */

    public List<GamePlatformBrandDto> findAllGamePlatformsByBrand() {
        return StreamSupport.stream(gamePlatformRepository.findAll().spliterator(), false)
                .map(gamePlatformDtoMapper::mapBrandDto)
                .sorted(Comparator.comparing(GamePlatformBrandDto::getBrand))
                .filter(dto -> !dto.getName().equalsIgnoreCase(ALL_CATEGORIES_NAME))
                .toList();
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
     * Counts the number of games for each platform and returns a sorted map.
     * This method uses the repository to find all platforms and count games for each platform.
     * It then streams the result to a sorted map, where the key is a mapped DTO (GamePlatformBrandDto)
     * and the value is the number of games.
     *
     * @return A sorted map where the key is a GamePlatformBrandDto and the value is the number of games.
     */

    public Map<GamePlatformBrandDto, Long> countAllGamesByPlatforms(String platform) {

        return gamePlatformRepository.countGamesByAllPlatforms(platform)
                .stream()
                .map(o -> new AbstractMap.SimpleEntry<>(
                        gamePlatformDtoMapper.mapBrandDto((GamePlatform) o[0]),
                        (Long) o[1]
                ))
                .filter(entry -> !entry.getKey().getName().equalsIgnoreCase(ALL_CATEGORIES_NAME))
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(GamePlatformBrandDto::getBrand)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
    public Map<GamePlatformBrandDto,Long> countAllGamesByPlatformsAndCategories(Set<String>categories,String platformName){
        return gamePlatformRepository.countGamesByAllPlatformsAndCategories(categories,categories.size(),platformName)
                .stream()
                .map(o -> new AbstractMap.SimpleEntry<>(
                        gamePlatformDtoMapper.mapBrandDto((GamePlatform) o[0]),
                        (Long) o[1]
                ))
                .filter(entry -> !entry.getKey().getName().equalsIgnoreCase(ALL_CATEGORIES_NAME))
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(GamePlatformBrandDto::getBrand)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
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

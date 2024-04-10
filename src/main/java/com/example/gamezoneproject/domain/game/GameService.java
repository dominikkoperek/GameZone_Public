package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.exceptions.CategoryNotFoundException;
import com.example.gamezoneproject.domain.exceptions.CompanyNotFoundException;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.dto.GameSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryRepository;
import com.example.gamezoneproject.domain.game.gameDetails.company.Company;
import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyRepository;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameMode;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameModeRepository;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformRepository;
import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import com.example.gamezoneproject.storage.FileStorageService;
import com.example.gamezoneproject.storage.ImageStorageFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * This class shares public methods which allow to manage games.
 * It uses the GameRepository, GamePlatformRepository, CompanyRepository, CategoryRepository, GameModeRepository
 * to interact with the database.
 */
@Service
public class GameService {
    private final FileStorageService fileStorageService;
    private final GameRepository gameRepository;
    private final GamePlatformRepository gamePlatformRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final GameModeRepository gameModeRepository;

    public GameService(FileStorageService fileStorageService, GameRepository gameRepository,
                       GamePlatformRepository gamePlatformRepository, CompanyRepository companyRepository,
                       CategoryRepository categoryRepository, GameModeRepository gameModeRepository) {
        this.fileStorageService = fileStorageService;
        this.gameRepository = gameRepository;
        this.gamePlatformRepository = gamePlatformRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.gameModeRepository = gameModeRepository;
    }

    /**
     * Finds all promoted games, and map them to GameDto.
     *
     * @return A list of promoted games mapped to GameDto.
     */
    public List<GameDto> findAllPromotedGames() {
        return gameRepository.findAllByPromotedIsTrue()
                .stream().map(GameDtoMapper::map)
                .toList();
    }

    /**
     * Finds game by id, and map it to GameDto.
     *
     * @param gameId The id of the game.
     * @return Option containing GameDto if game is found, or empty if not.
     */

    public Optional<GameDto> findById(Long gameId) {
        return gameRepository.findById(gameId)
                .map(GameDtoMapper::map);
    }

    /**
     * Finds game by closest premier day to present day, and map it to GameDto.
     *
     * @return Option containing GameDto if game is found, or empty if not.
     */
    public Optional<GameDto> findGameByClosestPremierDate() {
        return gameRepository.findGameByClosestReleaseDate()
                .map(GameDtoMapper::map);
    }

    /**
     * Finds all games by category name, and map it to GameDto.
     *
     * @param category name of the category.
     * @return List of GameDto by the category.
     */
    public List<GameDto> findGamesByCategoryName(String category) {
        return gameRepository.findAllByCategory_NameIgnoreCase(category)
                .stream()
                .map(GameDtoMapper::map)
                .toList();
    }

    /**
     * Finds all games by platform name, and map it to GameDto.
     *
     * @param gamePlatform name of the game platform.
     * @return List of GameDto by the game platform.
     */
    public List<GameDto> findGamesByGamePlatformName(String gamePlatform) {
        return gameRepository.findAllByGamePlatform_NameIgnoreCase(gamePlatform)
                .stream()
                .map(GameDtoMapper::map)
                .toList();
    }

    /**
     * Finds all games that are promoted by producer id, and map them to GameByCompanyDto.
     *
     * @param producerId The id of the producer.
     * @return List of all promoted Games by producer id.
     */
    public List<GameByCompanyDto> findAllPromotedGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_IdAndPromotedIsTrue(producerId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    /**
     * Finds all games that are promoted by publisher id, and map them to GameByCompanyDto.
     *
     * @param publisherId The id of the publisher.
     * @return List of all promoted Games by publisher id.
     */
    public List<GameByCompanyDto> findAllPromotedGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_IdAndPromotedIsTrue(publisherId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    /**
     * Finds all games, and map it to GameDto.
     *
     * @return List of all games mapped to GameDto.
     */
    public List<GameDto> findAllGames() {
        return StreamSupport
                .stream(gameRepository.findAll().spliterator(), false)
                .map(GameDtoMapper::map)
                .toList();

    }

    /**
     * Checks whether the game is already exists by name.
     *
     * @param gameTitle The game title.
     * @return True if the game not exists, and false if the game already is in database.
     */
    public boolean isTitleAvailable(String gameTitle) {
        return gameRepository.findByTitleIgnoreCase(gameTitle).isEmpty();
    }

    /**
     * Finds all games by producer id, map them to GameByCompanyDto and sort them by release year reversed.
     *
     * @param producerId The id of the producer.
     * @return List of games by producer id sorted by release year reversed.
     */
    public List<GameByCompanyDto> findAllGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_Id(producerId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .sorted(Comparator.comparing(GameByCompanyDto::getReleaseYear).reversed())
                .toList();
    }

    /**
     * Finds all games by publisher id, map them to GameByCompanyDto and sort them by release year reversed.
     *
     * @param publisherId The id of the publisher.
     * @return List of games by publisher id sorted by release year reversed.
     */
    public List<GameByCompanyDto> findAllGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_Id(publisherId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .sorted(Comparator.comparing(GameByCompanyDto::getReleaseYear).reversed())
                .toList();
    }

    /**
     * This method uses the repository to find game by name param.
     * It maps the result to a GameDto
     *
     * @param name name of the game.
     * @return Optional containing a GameDto if the name exists, or empty optional.
     */
    public Optional<GameDto> findByTitle(String name) {
        return gameRepository.findByTitleIgnoreCase(name).map(GameDtoMapper::map);
    }

    /**
     * This method adds a new game to the repository.
     * It sets the properties of the game from the provided GameSaveDto object and the main category string.
     * It also handles the case where the poster is not null and saves the image.
     *
     * @param gameSaveDto The DTO object containing the game details to be saved.
     */
    @Transactional
    public void addGame(GameSaveDto gameSaveDto) {
        Game game = new Game();
        game.setTitle(gameSaveDto.getTitle().trim());
        game.setShortDescription(gameSaveDto.getShortDescription().trim());
        game.setDescription(gameSaveDto.getDescription().trim());
        game.setDailymotionTrailerId(gameSaveDto.getDailymotionTrailerId().trim());

        game.setReleaseYear(getReleaseDate(gameSaveDto.getReleaseYear()));

        Company producer = companyRepository.findByNameIgnoreCase(gameSaveDto.getProducer())
                .orElseThrow(CompanyNotFoundException::new);
        game.setProducer(producer);

        Company publisher = companyRepository.findByNameIgnoreCase(gameSaveDto.getPublisher())
                .orElseThrow(CompanyNotFoundException::new);
        game.setPublisher(publisher);

        game.setCategory(getUserCategory(gameSaveDto.getCategory(), gameSaveDto.getMainCategory()));

        game.setGamePlatform(getUserPlatforms(gameSaveDto.getPlatform()));

        game.setGameModes(getGameModes(gameSaveDto.getGameModes()));

        game.setPlayerRange(getPlayersRange(gameSaveDto.getPlayerRange()));

        game.setPromoted(gameSaveDto.isPromoted());


        if (gameSaveDto.getPoster() != null && !gameSaveDto.getPoster().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(gameSaveDto.getPoster(), gameSaveDto.getTitle(),
                    ImageStorageFile.GAME_POSTER);
            game.setPoster(savedFileName);
        }
        gameRepository.save(game);
    }

    /**
     * This method gets the release date of the game.
     * If the release year is null, it returns a default date.
     *
     * @param releaseYear The release year of the game.
     * @return The release date of the game.
     */
    private LocalDate getReleaseDate(LocalDate releaseYear) {
        if (releaseYear == null) {
            return LocalDate.of(9999, 1, 1);
        }
        return LocalDate.of(releaseYear.getYear(), releaseYear.getMonth(), releaseYear.getDayOfMonth());
    }

    /**
     * This method gets the player range of the game.
     *
     * @param playerRange The player range of the game.
     * @return The player range of the game.
     */
    private PlayerRange getPlayersRange(PlayerRange playerRange) {
        PlayerRange result = new PlayerRange();
        result.setMinPlayers(playerRange.getMinPlayers());
        result.setMaxPlayers(playerRange.getMaxPlayers());
        return result;
    }

    /**
     * This method gets the game modes of the game.
     *
     * @param gameModesDto The list of game modes.
     * @return The list of game modes of the game.
     */
    private List<GameMode> getGameModes(List<String> gameModesDto) {
        List<GameMode> resultModes = new ArrayList<>();
        for (String gameMode : gameModesDto) {
            GameMode gameModeByName = gameModeRepository.findByNameIgnoreCase(gameMode).orElseThrow();
            resultModes.add(gameModeByName);
        }
        return resultModes;
    }

    /**
     * This method gets the platforms of the game.
     *
     * @param platformDto The set of platforms.
     * @return The set of platforms of the game.
     */
    private Set<GamePlatform> getUserPlatforms(Set<String> platformDto) {
        Set<GamePlatform> resultPlatforms = new HashSet<>();
        for (String gamePlatform : platformDto) {
            GamePlatform platformByName = gamePlatformRepository.findByNameIgnoreCase(gamePlatform).orElseThrow();
            resultPlatforms.add(platformByName);
        }
        return resultPlatforms;
    }

    /**
     * This method gets the categories of the game.
     * It also handles the case where the main category is present in the list of categories.
     *
     * @param categoriesDto The list of categories.
     * @param mainCategory  The main category of the game.
     * @return The list of categories of the game.
     */
    private List<Category> getUserCategory(List<String> categoriesDto, String mainCategory) {
        List<Category> resulCategories = new ArrayList<>();
        for (String category : categoriesDto) {
            Category categoryByName = categoryRepository.findByNameIgnoreCase(category).orElseThrow();
            resulCategories.add(categoryByName);
        }
        setMainCategory(categoriesDto, mainCategory, resulCategories);
        return resulCategories;
    }

    private void setMainCategory(List<String> categoriesDto, String mainCategory, List<Category> resulCategories) {
        if (mainCategory.isEmpty()) {
            mainCategory = categoriesDto.get(0);
        }

        Category mainCategoryToFind = categoryRepository.findByNameIgnoreCase(mainCategory).orElseThrow(CategoryNotFoundException::new);

        int indexOfMainCategory = resulCategories.indexOf(mainCategoryToFind);
        Category temp = resulCategories.get(indexOfMainCategory);
        resulCategories.remove(temp);
        resulCategories.addFirst(temp);
    }

}


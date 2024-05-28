package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.exceptions.CategoryNotFoundException;
import com.example.gamezoneproject.domain.exceptions.CompanyNotFoundException;
import com.example.gamezoneproject.domain.game.dto.*;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryRepository;
import com.example.gamezoneproject.domain.game.gameDetails.company.Company;
import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyRepository;
import com.example.gamezoneproject.domain.game.gameDetails.modes.GameMode;
import com.example.gamezoneproject.domain.game.gameDetails.modes.GameModeRepository;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformRepository;
import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendarRepository;
import com.example.gamezoneproject.storage.FileStorageService;
import com.example.gamezoneproject.storage.storageStrategy.BigPoster;
import com.example.gamezoneproject.storage.storageStrategy.GamePoster;
import com.example.gamezoneproject.storage.storageStrategy.SmallPoster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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
    private final ReleaseCalendarRepository releaseDateRepository;
    private final GameDtoMapper gameDtoMapper;
    private final GamePoster gamePoster;
    private final SmallPoster smallPoster;
    private final BigPoster bigPoster;

    public GameService(FileStorageService fileStorageService, GameRepository gameRepository,
                       GamePlatformRepository gamePlatformRepository, CompanyRepository companyRepository,
                       CategoryRepository categoryRepository, GameModeRepository gameModeRepository,
                       ReleaseCalendarRepository releaseDateRepository, GameDtoMapper gameDtoMapper, GamePoster gamePoster, SmallPoster smallPoster,
                       BigPoster bigPoster) {
        this.fileStorageService = fileStorageService;
        this.gameRepository = gameRepository;
        this.gamePlatformRepository = gamePlatformRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.gameModeRepository = gameModeRepository;
        this.releaseDateRepository = releaseDateRepository;
        this.gameDtoMapper = gameDtoMapper;
        this.gamePoster = gamePoster;
        this.smallPoster = smallPoster;
        this.bigPoster = bigPoster;
    }

    /**
     * Finds all promoted games, and map them to GameDto.
     *
     * @return A list of promoted games mapped to GameDto.
     */
    public List<GameDto> findAllPromotedGames() {
        return gameRepository.findAllByPromotedIsTrueSortedByReleaseDate()
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
     * Finds all games by category name, and map it to GameDto.
     *
     * @param category name of the category.
     * @return List of GameDto by the category.
     */
    public List<GameDto> findGamesByCategoryName(String category) {
        return gameRepository.findAllByCategoryNameSortedByReleaseDateIgnoreCase(category)
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
        return gameRepository.findAllGamesByPlatformSortedByReleaseDate(gamePlatform)
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
                .map(gameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    /**
     * Finds all games that are promoted by publisher id, and map them to GameByCompanyDto.
     *
     * @param publisherId The id of the publisher.
     * @return List of all promoted Games by publisher id.
     */
    public List<PromotedGameByCompanyDto> findAllPromotedGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_IdAndPromotedIsTrue(publisherId)
                .stream()
                .map(GameDtoMapper::mapPromotedGameByCompanyId)
                .toList();
    }

    /**
     * Finds all games, and map it to GameDto.
     *
     * @return List of all games mapped to GameDto.
     */
    public List<GameDto> findAllGamesSortedByOldestReleaseDate() {
        return gameRepository
                .findAllSortedByOldestReleaseDate().stream()
                .map(GameDtoMapper::map)
                .toList();

    }

    /**
     * Method sorting game release dates map by value (date) descending
     *
     * @param releaseDateMap map with all release dates for game.
     * @return sorted map with all release dates for game.
     */
    public static Map<String, LocalDate> sortReleaseDates(Map<String, LocalDate> releaseDateMap) {
        List<Map.Entry<String, LocalDate>> entryList = new ArrayList<>(releaseDateMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        Map<String, LocalDate> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, LocalDate> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
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
                .map(gameDtoMapper::mapGameByCompanyId)
                //   .sorted(Comparator.comparing().reversed()) ************* FIX SORTOWANIE
                .toList();
    }


    /**
     * Finds game by closest premier day to present day, and map it to GameSuggestionsDto.
     *
     * @return Option containing GameSuggestionsDto if game is found, or empty if not.
     */
    public Optional<GameSuggestionsDto> findGameByClosestPremierDate() {
        return gameRepository
                .findGameByClosestPremierDate()
                .map(gameDtoMapper::mapToGameSuggestionsDto);
    }

    /**
     * This method merge all release dates to 1 if the date is the same for platforms.
     *
     * @param gameDto Game dto object.
     * @return Merged map with  release date and list of platforms.
     */
    public Map<LocalDate, List<String>> mergeSameReleaseDates(GameDto gameDto) {
        Map<LocalDate, List<String>> resultMap = new TreeMap<>();
        for (Map.Entry<String, LocalDate> entry : gameDto.getReleaseYear().entrySet()) {
            String platform = entry.getKey();
            LocalDate date = entry.getValue();
            resultMap
                    .computeIfAbsent(date, k -> new ArrayList<>())
                    .add(platform);
        }
        return resultMap;
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
                .map(gameDtoMapper::mapGameByCompanyId)
                // .sorted(Comparator.comparing(GameByCompanyDto::getReleaseYear).reversed()) FIX
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
        game.setReleaseDate(getReleaseDate(gameSaveDto.getReleaseYear()));
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
                    gamePoster);
            game.setPoster(savedFileName);
        }
        if (gameSaveDto.getSmallPosterSuggestion() != null && !gameSaveDto.getSmallPosterSuggestion().isEmpty()) {
            String savedSmallPoster = fileStorageService.saveImage(gameSaveDto.getSmallPosterSuggestion(), gameSaveDto.getTitle(),
                    smallPoster);
            game.setSmallPosterSuggestion(savedSmallPoster);
        }
        if (gameSaveDto.getBigPosterSuggestion() != null && !gameSaveDto.getBigPosterSuggestion().isEmpty()) {
            String savedBigPoster = fileStorageService.saveImage(gameSaveDto.getBigPosterSuggestion(), gameSaveDto.getTitle(),
                    bigPoster);
            game.setBigPosterSuggestion(savedBigPoster);
        }
        gameRepository.save(game);
    }

    public Map<String,LocalDate> mapToReleaseDateMap(List<String> platformName, List<String> releaseDate) {
        Map<String,LocalDate> map = new HashMap<>();
        for (int i = 0; i < platformName.size(); i++) {
            map.put(platformName.get(i),LocalDate.parse(releaseDate.get(i)));
        }
        return map;
    }

    /**
     * This method gets the release date of the game.
     * If the release year is null, it returns a default date.
     *
     * @param releaseYear The release year of the game.
     * @return The release date of the game.
     */
    private List<ReleaseCalendar> getReleaseDate(Map<String, LocalDate> releaseYear) {
        List<ReleaseCalendar> premiereDates = new ArrayList<>();
        for (Map.Entry<String, LocalDate> localDateStringEntry : releaseYear.entrySet()) {
            LocalDate localDate = localDateStringEntry.getValue();
            String platform = localDateStringEntry.getKey();
            Optional<ReleaseCalendar> byReleaseDateAndGamePlatform = releaseDateRepository
                    .findByReleaseDateAndGamePlatform(localDate, platform);

            if (byReleaseDateAndGamePlatform.isPresent()) {
                premiereDates.add(byReleaseDateAndGamePlatform.get());
            } else {
                ReleaseCalendar releaseCalendar = new ReleaseCalendar();
                releaseCalendar.setReleaseDate(localDate);
                releaseCalendar.setGamePlatform(platform);
                premiereDates.add(releaseCalendar);
                releaseDateRepository.save(releaseCalendar);
            }

        }
        return premiereDates;
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
            mainCategory = categoriesDto.getFirst();
        }

        Category mainCategoryToFind = categoryRepository
                .findByNameIgnoreCase(mainCategory)
                .orElseThrow(CategoryNotFoundException::new);

        int indexOfMainCategory = resulCategories.indexOf(mainCategoryToFind);
        Category temp = resulCategories.get(indexOfMainCategory);
        resulCategories.remove(temp);
        resulCategories.addFirst(temp);
    }

}


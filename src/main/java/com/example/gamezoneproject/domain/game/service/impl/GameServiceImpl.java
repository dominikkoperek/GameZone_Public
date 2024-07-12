package com.example.gamezoneproject.domain.game.service.impl;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.game.GameDtoMapper;
import com.example.gamezoneproject.domain.game.GameRepository;
import com.example.gamezoneproject.domain.game.dto.page.BaseGameDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageApiDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.dto.page.PageDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.exceptions.CategoryNotFoundException;
import com.example.gamezoneproject.exceptions.CompanyNotFoundException;
import com.example.gamezoneproject.exceptions.PlatformNotFoundException;
import com.example.gamezoneproject.domain.game.dto.*;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryRepository;
import com.example.gamezoneproject.domain.company.Company;
import com.example.gamezoneproject.domain.company.CompanyRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

/**
 * This class shares public methods which allow to manage content.
 * It uses the GameRepository, GamePlatformRepository, CompanyRepository, CategoryRepository, GameModeRepository
 * to interact with the database.
 */
@Service
public class GameServiceImpl implements GameService {
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

    public GameServiceImpl(FileStorageService fileStorageService, GameRepository gameRepository,
                           GamePlatformRepository gamePlatformRepository, CompanyRepository companyRepository,
                           CategoryRepository categoryRepository, GameModeRepository gameModeRepository,
                           ReleaseCalendarRepository releaseDateRepository, GameDtoMapper gameDtoMapper,
                           GamePoster gamePoster, SmallPoster smallPoster, BigPoster bigPoster) {
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
     * Finds all promoted content, and map them to GameDto.
     *
     * @return A list of promoted content mapped to GameDto.
     */
    @Override
    public GamePageDto findAllPromotedGames(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Game> allGames = gameRepository
                .findAllByPromotedIsTrueSortedByReleaseDate(pageable);
        List<GameDto> content = allGames
                .getContent()
                .stream()
                .map(gameDtoMapper::map)
                .toList();;
        return createPageResponse(content,allGames,GamePageDto::new);
    }

    /**
     * Finds game by id, and map it to GameDto.
     *
     * @param gameId The id of the game.
     * @return Option containing GameDto if game is found, or empty if not.
     */
    @Override
    public Optional<GameDto> findById(Long gameId) {
        return gameRepository.findById(gameId)
                .map(gameDtoMapper::map);
    }

    /**
     * Finds all content by category name, and map it to GameDto.
     *
     * @param category name of the category.
     * @return List of GameDto by the category.
     */
    @Override
    public GamePageDto findGamesByCategoryName(String category,int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Game> allGamesByCategory = gameRepository
                .findAllByCategoryNameSortedByReleaseDateIgnoreCase(category, pageable);
        List<GameDto> content = allGamesByCategory
                .getContent()
                .stream()
                .map(gameDtoMapper::map)
                .toList();
        return createPageResponse(content,allGamesByCategory,GamePageDto::new);
    }

    /**
     * Finds all content by platform name, and map it to GameDto.
     *
     * @param gamePlatform name of the game platform.
     * @return List of GameDto by the game platform.
     */
    @Override
    public GamePageDto findGamesByGamePlatformName(String gamePlatform,int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Game> allGamesByPlatform = gameRepository
                .findAllGamesByPlatformSortedByReleaseDate(gamePlatform, pageable);
        List<GameDto> content = allGamesByPlatform
                .getContent()
                .stream()
                .map(gameDtoMapper::map)
                .toList();;
        return createPageResponse(content,allGamesByPlatform,GamePageDto::new);
}

    /**
     * Finds all content that are promoted by producer id, and map them to GameByCompanyDto.
     *
     * @param producerId The id of the producer.
     * @return List of all promoted Games by producer id.
     */
    @Override
    public List<GameByCompanyDto> findAllPromotedGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_IdAndPromotedIsTrue(producerId)
                .stream()
                .map(gameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    /**
     * Finds all content that are promoted by publisher id, and map them to GameByCompanyDto.
     *
     * @param publisherId The id of the publisher.
     * @return List of all promoted Games by publisher id.
     */
    @Override
    public List<PromotedGameByCompanyDto> findAllPromotedGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_IdAndPromotedIsTrue(publisherId)
                .stream()
                .map(gameDtoMapper::mapPromotedGameByCompanyId)
                .toList();
    }

    /**
     * Finds all content, and map it to GameDto.
     *
     * @return List of all content mapped to GameDto.
     */
    @Override
    public GamePageDto findAllGamesSortedByOldestReleaseDate(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Game> allGameSorted = gameRepository.findAllSortedByOldestReleaseDate(pageable);
        List<GameDto> content = allGameSorted.getContent().stream().map(gameDtoMapper::map).toList();;
        return createPageResponse(content, allGameSorted, GamePageDto::new);
    }

    @Override
    public List<GameApiDto> findAllGamesApi() {
        return gameRepository
                .findAll()
                .stream()
                .map(gameDtoMapper::mapToApiDto)
                .toList();
    }

    @Override
    public GamePageApiDto findAllGamesSortedByOldestReleaseDateApi(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Game> allGamesSorted = gameRepository.findAllSortedByOldestReleaseDate(pageable);
        List<GameApiDto> content = allGamesSorted
                .getContent()
                .stream()
                .map(gameDtoMapper::mapToApiDto)
                .toList();;
        return createPageResponse(content, allGamesSorted, GamePageApiDto::new);
    }

    private <T extends BaseGameDto, D extends PageDto<T>> D createPageResponse(List<T> content,
                                                                               Page<Game> allGamesSorted,
                                                                               Supplier<D> supplier) {
        D pageDto = supplier.get();
        pageDto.setGames(content);
        pageDto.setTotalPages(allGamesSorted.getTotalPages());
        pageDto.setTotalElements(allGamesSorted.getTotalElements());
        pageDto.setPageNo(allGamesSorted.getNumber());
        pageDto.setPageSize(allGamesSorted.getSize());
        pageDto.setFirstPage(allGamesSorted.isFirst());
        pageDto.setLastPage(allGamesSorted.isLast());
        return pageDto;
    }

    /**
     * Checks whether the game is already exists by name.
     *
     * @param gameTitle The game title.
     * @return True if the game not exists, and false if the game already is in database.
     */
    @Override
    public boolean isTitleAvailable(String gameTitle) {
        return gameRepository.findByTitleIgnoreCase(gameTitle).isEmpty();
    }

    /**
     * Finds all content by producer id, map them to GameByCompanyDto and sort them by release year reversed.
     *
     * @param producerId The id of the producer.
     * @return List of content by producer id sorted by release year reversed.
     */
    @Override
    public List<GameByCompanyDto> findAllGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_IdOrderByReleaseDate_ReleaseDateDesc(producerId)
                .stream()
                .map(gameDtoMapper::mapGameByCompanyId)
                .toList();
    }


    /**
     * Finds game by closest premier day to present day, and map it to GameSuggestionsDto.
     *
     * @return Option containing GameSuggestionsDto if game is found, or empty if not.
     */
    @Override
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
    @Override
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
     * Finds all content by publisher id, map them to GameByCompanyDto and sort them by release year reversed.
     *
     * @param publisherId The id of the publisher.
     * @return List of content by publisher id sorted by release year reversed.
     */
    @Override
    public List<GameByCompanyDto> findAllGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_IdOrderByReleaseDate_ReleaseDateDesc(publisherId)
                .stream()
                .map(gameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    /**
     * This method uses the repository to find game by name param.
     * It maps the result to a GameDto
     *
     * @param name name of the game.
     * @return Optional containing a GameDto if the name exists, or empty optional.
     */
    @Override
    public Optional<GameDto> findByTitle(String name) {
        return gameRepository.findByTitleIgnoreCase(name).map(gameDtoMapper::map);
    }

    /**
     * This method adds a new game to the repository.
     * It sets the properties of the game from the provided GameSaveDto object and the main category string.
     * It also handles the case where the poster is not null and saves the image.
     *
     * @param gameSaveDto The DTO object containing the game details to be saved.
     */
    @Override
    @Transactional
    public void addGame(GameSaveDto gameSaveDto) {
        Game game = new Game();
        game.setTitle(gameSaveDto.getTitle().trim());
        game.setShortDescription(gameSaveDto.getShortDescription().trim());
        game.setDescription(gameSaveDto.getDescription().trim());
        game.setDailymotionTrailerId(gameSaveDto.getDailymotionTrailerId().trim());
        game.setReleaseDate(getReleaseCalendar(gameSaveDto.getReleaseYear()));
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

    /**
     * This method map 2-String lists into map where key is String platform name and value is Local date release date.
     *
     * @param platformName List of platform names of the game.
     * @param releaseDate  List of Strings release dates.
     * @return Map of Platform names and parsed dates.
     */
    @Override
    public Map<String, LocalDate> mapToReleaseDateMap(List<String> platformName, List<String> releaseDate) {
        Map<String, LocalDate> map = new HashMap<>();
        for (int i = 0; i < platformName.size(); i++) {
            map.put(platformName.get(i), LocalDate.parse(releaseDate.get(i)));
        }
        return map;
    }

    /**
     * This method parse map of string,localdate to ReleaseCalendar object and add it to list.
     * Method finds platform by name and release year in database if such calendar exists add it to list if not
     * create a new one and then add it to list.
     *
     * @param releaseYear Map of platforms names and release dates to be parsed.
     * @return List of ReleaseCalender for game.
     */
    private List<ReleaseCalendar> getReleaseCalendar(Map<String, LocalDate> releaseYear) {
        List<ReleaseCalendar> premiereDates = new ArrayList<>();
        for (Map.Entry<String, LocalDate> localDateStringEntry : releaseYear.entrySet()) {
            LocalDate localDate = localDateStringEntry.getValue();
            GamePlatform gamePlatform = gamePlatformRepository
                    .findByNameIgnoreCase(localDateStringEntry.getKey())
                    .orElseThrow(PlatformNotFoundException::new);
            Optional<ReleaseCalendar> calendarByPlatformNameAndDate = releaseDateRepository
                    .findByReleaseDateAndGamePlatform_Name(localDate, gamePlatform.getName());

            if (calendarByPlatformNameAndDate.isPresent()) {
                premiereDates.add(calendarByPlatformNameAndDate.get());
            } else {
                ReleaseCalendar releaseCalendar = getReleaseCalendar(localDate, gamePlatform);
                premiereDates.add(releaseCalendar);
                releaseDateRepository.save(releaseCalendar);
            }
        }
        return premiereDates;
    }

    private static ReleaseCalendar getReleaseCalendar(LocalDate localDate, GamePlatform gamePlatform) {
        ReleaseCalendar releaseCalendar = new ReleaseCalendar();
        releaseCalendar.setReleaseDate(localDate);
        releaseCalendar.setGamePlatform(gamePlatform);
        return releaseCalendar;
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


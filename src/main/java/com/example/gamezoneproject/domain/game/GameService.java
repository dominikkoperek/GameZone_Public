package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.dto.GameSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryRepository;
import com.example.gamezoneproject.domain.game.gameDetails.company.Company;
import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyRepository;
import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode.GameGameMode;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode.GameGameModeRepository;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameMode;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameModeRepository;
import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformRepository;
import com.example.gamezoneproject.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class GameService {
    private final FileStorageService fileStorageService;
    private final GameRepository gameRepository;
    private final GamePlatformRepository gamePlatformRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final GameGameModeRepository gameGameModeRepository;
    private final GameModeRepository gameModeRepository;

    public GameService(FileStorageService fileStorageService, GameRepository gameRepository,
                       GamePlatformRepository gamePlatformRepository, CompanyRepository companyRepository,
                       CategoryRepository categoryRepository, GameGameModeRepository gameGameModeRepository, GameModeRepository gameModeRepository) {
        this.fileStorageService = fileStorageService;
        this.gameRepository = gameRepository;
        this.gamePlatformRepository = gamePlatformRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.gameGameModeRepository = gameGameModeRepository;
        this.gameModeRepository = gameModeRepository;
    }

    public List<GameDto> findAllPromotedGames() {
        return gameRepository.findAllByPromotedIsTrue()
                .stream()
                .map(GameDtoMapper::map)
                .toList();
    }

    public Optional<GameDto> findById(Long gameId) {
        return gameRepository.findById(gameId).map(GameDtoMapper::map);
    }

    public List<GameDto> findGamesByCategoryName(String category) {
        return gameRepository.findAllByCategory_NameIgnoreCase(category)
                .stream()
                .map(GameDtoMapper::map)
                .toList();
    }

    public List<GameDto> findGamesByGamePlatformName(String gamePlatform) {
        return gameRepository.findAllByGamePlatform_NameIgnoreCase(gamePlatform)
                .stream()
                .map(GameDtoMapper::map)
                .toList();
    }

    public List<GameByCompanyDto> findAllPromotedGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_IdAndPromotedIsTrue(producerId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    public List<GameByCompanyDto> findAllPromotedGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_IdAndPromotedIsTrue(publisherId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .toList();
    }

    public List<GameDto> findAllGames() {
        return StreamSupport.stream(gameRepository.findAll().spliterator(), false)
                .map(GameDtoMapper::map)
                .toList();

    }

    public List<GameByCompanyDto> findAllGamesByProducerId(Long producerId) {
        return gameRepository.findAllByProducer_Id(producerId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .sorted(Comparator.comparing(GameByCompanyDto::getReleaseYear).reversed())
                .toList();
    }

    public List<GameByCompanyDto> findAllGamesByPublisherId(Long publisherId) {
        return gameRepository.findAllByPublisher_Id(publisherId)
                .stream()
                .map(GameDtoMapper::mapGameByCompanyId)
                .toList();
    }
    @Transactional
    public void addGame(GameSaveDto gameSaveDto) {
        Game game = new Game();
        game.setTitle(gameSaveDto.getTitle());
        game.setShortDescription(gameSaveDto.getShortDescription());
        game.setDescription(gameSaveDto.getDescription());
        game.setDailymotionTrailerId(gameSaveDto.getDailymotionTrailerId());
        game.setReleaseYear(gameSaveDto.getReleaseYear());

        Company producer = companyRepository.findByNameIgnoreCase(gameSaveDto.getProducer()).orElseThrow();
        game.setProducer(producer);

        Company publisher = companyRepository.findByNameIgnoreCase(gameSaveDto.getPublisher()).orElseThrow();
        game.setPublisher(publisher);

        game.setCategory(getUserCategory(gameSaveDto.getCategory()));

        game.setGamePlatform(getUserPlatforms(gameSaveDto.getPlatform()));

        game.setGameModes(getGameModes(gameSaveDto.getGameModes(),game));

        game.setPromoted(gameSaveDto.isPromoted());
        if (gameSaveDto.getPoster() != null && !gameSaveDto.getPoster().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(gameSaveDto.getPoster());
            game.setPoster(savedFileName);
        }
        gameRepository.save(game);
    }

    private List<GameGameMode> getGameModes(List<GameModeSaveDto> gameModesDto, Game game) {

        List<GameGameMode> resultModes = new ArrayList<>();
        for (GameModeSaveDto gameMode : gameModesDto) {
            GameMode gameModeByName = gameModeRepository.findByNameIgnoreCase(gameMode.getName()).orElseThrow();

            GameGameMode gameGameMode = new GameGameMode();
            gameGameMode.setGame(game);
            gameGameMode.setGameMode(gameModeByName);
            gameGameMode.setDescription(gameMode.getDescription());
            resultModes.add(gameGameMode);
        }
        return resultModes;
    }

    private Set<GamePlatform> getUserPlatforms(Set<String> platformDto) {
        Set<GamePlatform> resultPlatforms = new HashSet<>();
        for (String gamePlatform : platformDto) {
            GamePlatform platformByName = gamePlatformRepository
                    .findByNameIgnoreCase(gamePlatform).orElseThrow();
            resultPlatforms.add(platformByName);
        }
        return resultPlatforms;
    }

    private List<Category> getUserCategory(List<String> categoriesDto) {
        List<Category> resulCategories = new ArrayList<>();
        for (String category : categoriesDto) {
            Category categoryByName = categoryRepository
                    .findByNameIgnoreCase(category)
                    .orElseThrow();
            resulCategories.add(categoryByName);
        }
        return resulCategories;
    }

}


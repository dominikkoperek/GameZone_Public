package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.game.GameRepository;
import com.example.gamezoneproject.domain.game.GameSpecs;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformBrandDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.web.admin.LoginController;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/gry")
public class GameSearchController {
    private final static short MAX_CATEGORY_SIZE = 10;
    private static final String BEFORE_RELEASE_URL = "/b";
    private static final String AFTER_RELEASE_URL = "/a";
    private final GamePlatformService gamePlatformService;
    private final GameService gameService;
    private final CategoryService categoryService;
    private final GameRepository gameRepository;


    public GameSearchController(GamePlatformService gamePlatformService, GameService gameService, CategoryService categoryService, GameRepository gameRepository) {
        this.gamePlatformService = gamePlatformService;
        this.gameService = gameService;
        this.categoryService = categoryService;
        this.gameRepository = gameRepository;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:/gry/wyszukiwarka";
    }

    @GetMapping({"/wyszukiwarka", "/wyszukiwarka/{platformName}"})
    public String showSearch(
            @PathVariable(required = false) String platformName,
            @RequestParam(required = false) List<String> cat,
            @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                    defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                    defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (pageNo <= 0 || pageSize <= 0 ||
                (platformName != null && gamePlatformService.findGamePlatformByName(platformName).isEmpty())) {
            return "redirect:/gry/wyszukiwarka";
        }
        Set<String> normalizedCategories;
        Map<GamePlatformBrandDto, Long> gamesCounterByPlatform;
        if (cat == null || cat.isEmpty()) {
            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatforms(platformName);
            normalizedCategories = null;
        } else {
            normalizedCategories = categoryService.getNormalizedCategories(cat);
            addToModelFirstCategoryFromList(model, normalizedCategories);
            if (normalizedCategories.size() > MAX_CATEGORY_SIZE || normalizedCategories.size() != cat.size()) {
                return "redirect:/gry/wyszukiwarka";
            }
            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatformsAndCategories(normalizedCategories, platformName);
            for (String category : normalizedCategories) {
                if (categoryService.findCategoryByName(category).isEmpty()) {
                    return "redirect:/gry/wyszukiwarka";
                }
            }
            if (cat.size() != normalizedCategories.size()) {
                redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                        "Kategorie i/lub platforma nieistnieje!");
                return "redirect:/gry/wyszukiwarka";
            }
        }
        GamePageDto games = gameService.findAllGamesFilteredAndSorted(platformName, normalizedCategories,
                true, pageNo, pageSize);
        List<GamePlatformBrandDto> allGamePlatforms = gamePlatformService.findAllGamePlatformsByBrand();
//        List<Game> all = gameRepository.findAll(GameSpecs.hasBeenReleased(true, platformName)
//                .and(GameSpecs.hasPlatform(platformName))
//                .and(GameSpecs.hasCategories(normalizedCategories)));
//        for (Game game : all) {
//            System.err.println(game.getTitle());
//        }
//        System.err.println(all.size());

        addToSearchModel(model, allGamePlatforms, games, platformName != null ? platformName.toUpperCase() : null,
                pageNo, normalizedCategories, gamesCounterByPlatform);

        return "game-search";
    }

//    @GetMapping({"/wyszukiwarka/", "/wyszukiwarka"})
//    public String showSearch(Model model,
//                             @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
//                                     defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
//                             @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
//                                     defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
//                             @RequestParam(required = false) List<String> cat,
//                             RedirectAttributes redirectAttributes) {
//
//
//        Set<String> normalizedCategories;
//        Map<GamePlatformBrandDto, Long> gamesCounterByPlatform;
//        if (pageNo <= 0 || pageSize <= 0) {
//            return "redirect:/gry/wyszukiwarka";
//        }
//        if (cat == null || cat.isEmpty()) {
//            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatforms(null);
//            normalizedCategories = null;
//        } else {
//            normalizedCategories = categoryService.getNormalizedCategories(cat);
//            addToModelFirstCategoryFromList(model, normalizedCategories);
//            if (normalizedCategories.size() > MAX_CATEGORY_SIZE) {
//                return "redirect:/gry/wyszukiwarka";
//            }
//            gamesCounterByPlatform = gamePlatformService.
//                    countAllGamesByPlatformsAndCategories(normalizedCategories, null);
//            for (String category : normalizedCategories) {
//                if (categoryService.findCategoryByName(category).isEmpty() || hasDuplicates(normalizedCategories)) {
//                    return "redirect:/gry/wyszukiwarka";
//                }
//            }
//            if (cat.size() != normalizedCategories.size()) {
//                redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
//                        "Kategorie i/lub platforma nieistnieje!");
//                return "redirect:/gry/wyszukiwarka";
//            }
//        }
//        GamePageDto games = gameService.findAllGamesFilteredAndSorted(null, normalizedCategories, null, pageNo, pageSize);
//        List<GamePlatformBrandDto> allGamePlatforms = gamePlatformService.findAllGamePlatformsByBrand();
//        addToSearchModel(model, allGamePlatforms, games, null, pageNo, normalizedCategories, gamesCounterByPlatform);
//        return "game-search";
//    }

//    @GetMapping("/wyszukiwarka/{platformName}")
//    public String showSearchByPlatform(@PathVariable String platformName,
//                                       @RequestParam(required = false) List<String> cat,
//                                       @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
//                                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
//                                       @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
//                                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
//                                       Model model, RedirectAttributes redirectAttributes) {
//
//        if (pageNo <= 0 || pageSize <= 0 || gamePlatformService.findGamePlatformByName(platformName).isEmpty()) {
//            return "redirect:/gry/wyszukiwarka";
//        }
//
//        Set<String> normalizedCategories;
//        Map<GamePlatformBrandDto, Long> gamesCounterByPlatform;
//
//        if (cat == null || cat.isEmpty()) {
//            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatforms(platformName);
//            normalizedCategories = null;
//        } else {
//            normalizedCategories = categoryService.getNormalizedCategories(cat);
//            addToModelFirstCategoryFromList(model, normalizedCategories);
//            if (normalizedCategories.size() > MAX_CATEGORY_SIZE) {
//                return "redirect:/gry/wyszukiwarka";
//            }
//            gamesCounterByPlatform = gamePlatformService.
//                    countAllGamesByPlatformsAndCategories(normalizedCategories, platformName);
//            for (String category : normalizedCategories) {
//                if (categoryService.findCategoryByName(category).isEmpty() || hasDuplicates(normalizedCategories)) {
//                    return "redirect:/gry/wyszukiwarka";
//                }
//            }
//            if (cat.size() != normalizedCategories.size()) {
//                redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
//                        "Kategorie i/lub platforma nieistnieje!");
//                return "redirect:/gry/wyszukiwarka";
//            }
//        }
//
//        List<Game> all = gameRepository.findAll(GameSpecs.filterGames(platformName, normalizedCategories, null));
//        for (Game game : all) {
//            System.err.println(game.getTitle());
//        }
//        GamePageDto games = gameService
//                .findAllGamesFilteredAndSorted(platformName, normalizedCategories, null, pageNo, pageSize);
//        List<GamePlatformBrandDto> allGamePlatforms = gamePlatformService
//                .findAllGamePlatformsByBrand();
//        addToSearchModel(model, allGamePlatforms, games, platformName.toUpperCase(), pageNo,
//                normalizedCategories, gamesCounterByPlatform);
//        return "game-search";
//    }

    private void addToModelFirstCategoryFromList(Model model, Set<String> cat) {
        if (!cat.isEmpty()) {
            categoryService.findCategoryByName(cat.iterator().next())
                    .ifPresent(firstCat -> model.addAttribute("firstCategory", firstCat));
        }
    }

    private void addToSearchModel(Model model, List<GamePlatformBrandDto> allGamePlatforms,
                                  GamePageDto games, String platformName, int pageNo,
                                  Set<String> categories, Map<GamePlatformBrandDto, Long> gamesCounter) {
        model.addAttribute("categories", categories);
        if (categories != null) {
            model.addAttribute("categoriesParam", String.join(",", categories));
        }
        model.addAttribute("svgPlatforms", allGamePlatforms);
        model.addAttribute("content", games);
        model.addAttribute("sectionDescription", "Wyszukiwarka");
        model.addAttribute("heading", "Wyszukiwarka");
        model.addAttribute("displayGameListNav", true);
        model.addAttribute("currentPlatform", platformName);
        model.addAttribute("totalPages", Math.max(pageNo, games.getTotalPages()));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("nextPage", pageNo + 1);
        model.addAttribute("prevPage", Math.max(pageNo - 1, 1));
        model.addAttribute("gamesCounter", gamesCounter);
    }


    @GetMapping({"/test/{platform}" + BEFORE_RELEASE_URL, "/test/{platform}" + AFTER_RELEASE_URL})
    public String getGamesByPlatform(@PathVariable String platform,
                                     @RequestParam(required = false) List<String> cat,
                                     @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                             defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                             defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
                                     HttpServletRequest request,
                                     Model model) {

        int nextPage = pageNo + 1;
        int previousPage = Math.max(pageNo - 1, 1);
        Boolean afterRelease = null;
        Set<String> normalizedCat;
        if (cat != null) {
            normalizedCat = categoryService.getNormalizedCategories(cat);
        } else {
            normalizedCat = null;
        }
        if (request.getRequestURI().endsWith(BEFORE_RELEASE_URL)) {
            afterRelease = false;
        } else if (request.getRequestURI().endsWith(AFTER_RELEASE_URL)) {
            afterRelease = true;
        }
        GamePageDto gamePage = gameService.findAllGamesFilteredAndSorted(platform, normalizedCat, afterRelease, pageNo, pageSize);
        int totalPages = Math.max(pageNo, gamePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", previousPage);
        model.addAttribute("nextPageLink", "test");
        model.addAttribute("content", gamePage);
        model.addAttribute("heading", "Encyklopedia gier");

        return "game-listing";
    }
}

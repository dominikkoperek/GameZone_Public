package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformBrandDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping("/gry")
public class GameSearchController {
    private final static short MAX_CATEGORY_SIZE = 10;
    private final GamePlatformService gamePlatformService;
    private final GameService gameService;
    private final CategoryService categoryService;

    public GameSearchController(GamePlatformService gamePlatformService, GameService gameService, CategoryService categoryService) {
        this.gamePlatformService = gamePlatformService;
        this.gameService = gameService;
        this.categoryService = categoryService;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:/gry/wyszukiwarka";
    }

    @GetMapping({"/wyszukiwarka/", "/wyszukiwarka"})
    public String showSearch(Model model,
                             @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                     defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                             @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                     defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
                             @RequestParam(required = false) List<String> cat) {

        List<GamePlatformBrandDto> allGamePlatforms = gamePlatformService.findAllGamePlatformsByBrand();
        GamePageDto games;
        Map<GamePlatformBrandDto, Long> gamesCounterByPlatform;
        if (pageNo <= 0 || pageSize <= 0) {
            return "redirect:/gry/wyszukiwarka";
        }
        if (cat == null || cat.isEmpty()) {
            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatforms(null);
            games = gameService.findAllSortedById(pageNo, pageSize);
        } else {
            if (cat.size() > MAX_CATEGORY_SIZE) {
                return "redirect:/gry/wyszukiwarka";
            }
            gamesCounterByPlatform = gamePlatformService.
                    countAllGamesByPlatformsAndCategories(cat.stream().map(String::toLowerCase).toList(), null);
            for (String category : cat) {
                if (categoryService.findCategoryByName(category).isEmpty() || hasDuplicates(cat)) {
                    return "redirect:/gry/wyszukiwarka";
                }
            }
            List<String> lowerCaseCat = getLowerCaseCategories(model, cat);
            games = gameService.findAllGamesByCategories(lowerCaseCat, pageNo, pageSize);
        }

        addToSearchModel(model, allGamePlatforms, games, null, pageNo, cat, gamesCounterByPlatform);
        return "game-search";
    }

    private boolean hasDuplicates(List<String> cat) {
        Set<String> set = new HashSet<>(cat
                .stream()
                .map(String::toLowerCase)
                .toList());
        return set.size() < cat.size();
    }

    private List<String> getLowerCaseCategories(Model model, List<String> cat) {
        Optional<CategoryDto> findFirstCategoryName = categoryService.findCategoryByName(cat.getFirst());
        findFirstCategoryName.ifPresent(categoryDto -> model.addAttribute("firstCategory", categoryDto));
        return cat
                .stream()
                .map(String::toLowerCase)
                .toList();
    }


    @GetMapping("/wyszukiwarka/{platformName}")
    public String showSearchByPlatform(@PathVariable String platformName,
                                       @RequestParam(required = false) List<String> cat,
                                       @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
                                       @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                                       Model model) {
        if (pageNo <= 0 || pageSize <= 0) {
            return "redirect:/gry/wyszukiwarka";
        }
        if (gamePlatformService.findGamePlatformByName(platformName).isEmpty()) {
            return "redirect:/gry/wyszukiwarka";
        }

        GamePageDto games;
        List<GamePlatformBrandDto> allGamePlatforms = gamePlatformService.findAllGamePlatformsByBrand();
        Map<GamePlatformBrandDto, Long> gamesCounterByPlatform;

        if (cat == null || cat.isEmpty()) {
            games = gameService.findGamesByGamePlatformName(platformName, pageNo, pageSize);
            gamesCounterByPlatform = gamePlatformService.countAllGamesByPlatforms(platformName);
        } else {
            if (cat.size() > MAX_CATEGORY_SIZE) {
                return "redirect:/gry/wyszukiwarka";
            }
            gamesCounterByPlatform = gamePlatformService.
                    countAllGamesByPlatformsAndCategories(cat.stream().map(String::toLowerCase).toList(), platformName);
            for (String category : cat) {
                if (categoryService.findCategoryByName(category).isEmpty() || hasDuplicates(cat)) {
                    return "redirect:/gry/wyszukiwarka";
                }
            }
            List<String> categoriesLowerCase = getLowerCaseCategories(model, cat);
            games = gameService.findGamesByPlatformAndCategories(platformName, categoriesLowerCase, pageNo, pageSize);
        }
        addToSearchModel(model, allGamePlatforms, games, platformName.toUpperCase(), pageNo, cat, gamesCounterByPlatform);
        return "game-search";
    }

    private void addToSearchModel(Model model, List<GamePlatformBrandDto> allGamePlatforms,
                                  GamePageDto games, String platformName, int pageNo,
                                  List<String> categories, Map<GamePlatformBrandDto, Long> gamesCounter) {
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

    @GetMapping("/test/{platform}")
    public String getGamesByPlatform(@PathVariable String platform,
                                     @RequestParam(defaultValue = "akcji") List<String> cat,
                                     @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                             defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                             defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize,
                                     Model model) {
        int nextPage = pageNo + 1;
        int previousPage = Math.max(pageNo - 1, 1);
        List<String> categoriesLowerCase = cat
                .stream()
                .map(String::toLowerCase)
                .toList();
        GamePageDto gamePage = gameService.findGamesByPlatformAndCategories(platform, categoriesLowerCase, pageNo, pageSize);
        int totalPages = gamePage.getTotalPages();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", previousPage);
        model.addAttribute("nextPageLink", "test");
        model.addAttribute("content", gamePage);
        model.addAttribute("heading", "Encyklopedia gier");
        if (pageNo > totalPages) {
            return "redirect:/";
        }
        return "game-listing";
    }
}

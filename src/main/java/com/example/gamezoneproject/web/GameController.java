package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.dto.GameSuggestionsDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.domain.rating.RatingService;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Controller for game.
 */
@Controller
public class GameController {
    private final GameService gameService;
    private final RatingService ratingService;
    private final GamePlatformService gamePlatformService;


    public GameController(GameService gameService, RatingService ratingService, GamePlatformService gamePlatformService) {
        this.gameService = gameService;
        this.ratingService = ratingService;
        this.gamePlatformService = gamePlatformService;
    }

    @GetMapping({"/gry", "/gry/"})
    public String home(Model model,
                       @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                       @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize) {
        if (pageNo <= 0 || pageSize <= 0) {
            return "redirect:/gry";
        }
        int nextPage = pageNo + 1;
        int previousPage = Math.max(pageNo - 1,1);
        GamePageDto allGames = gameService.findAllGamesSortedByOldestReleaseDate(pageNo, pageSize);
        int totalPages = allGames.getTotalPages();
        addModelAttributes(model, allGames, pageNo, totalPages, nextPage, previousPage);

        if (pageNo > totalPages) {
            return ("redirect:/gry" + GlobalControllerAdvice.PAGE_PATH_QUERY + totalPages);
        }
        return "game-listing";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:/gry";
    }

    private void addModelAttributes(Model model, GamePageDto allGames, int currentPage,
                                    int totalPages, int nextPage, int prevPage) {
        model.addAttribute("nextPageLink", "/gry"+GlobalControllerAdvice.PAGE_PATH_QUERY);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();
        model.addAttribute("platforms", gamePlatforms);
        model.addAttribute("heading", "Wielka encyklopedia gier");
        model.addAttribute("description", "Encyklopedia Gier GameZone zawiera opisy, screeny," +
                " trailery, daty premier, wymagania sprzętowe, oceny i recenzje gier zarówno przed, jak i po premierze. Wszystkie gry w jednym miejscu!");
        model.addAttribute("content", allGames);
        model.addAttribute("allPlatforms", "Wszystkie");

        GameSuggestionsDto gameByClosestPremierDate = gameService
                .findGameByClosestPremierDate()
                .orElse(null);
        model.addAttribute("closestGameReleaseDate", gameByClosestPremierDate);
        model.addAttribute("sectionDescription", "Encyklopedia gier");
        model.addAttribute("displayGameListNav", true);
    }

    /**
     * Display game.
     *
     * @param id        The id of the game.
     * @param gameTitle The title of the game.
     * @param model     The Model object that add attributes.
     * @return The view name of the game.
     */
    @GetMapping("/gry/{gameTitle}/{id}")
    public String game(@PathVariable Long id,
                       @PathVariable String gameTitle,
                       Model model,
                       Authentication authentication) {
        GameDto gameDto = gameService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (authentication != null) {
            Double userRate = ratingService.getUserRatingForGame(authentication.getName(), id).orElse(null);
            model.addAttribute("userRate", userRate);
        }

        if (gameTitle.replaceAll("-", " ").equalsIgnoreCase(gameDto.getTitle())) {
            model.addAttribute("game", gameDto);
            model.addAttribute("gameTitle", gameDto.getTitle());
            model.addAttribute("gameReleaseDates", gameService.mergeSameReleaseDates(gameDto));
            model.addAttribute("allRates", ratingService.getAllRatesForGame(id));
            model.addAttribute("sectionDescription", "Encyklopedia gier");
            model.addAttribute("displayGameListNav", true);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "game";
    }
}

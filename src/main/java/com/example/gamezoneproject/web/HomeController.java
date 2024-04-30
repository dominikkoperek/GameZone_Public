package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.exceptions.GameNotFoundException;
import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameSuggestionsDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Home controller for all games.
 */
@Controller
public class HomeController {
    private final GameService gameService;
    private final GamePlatformService gamePlatformService;

    public HomeController(GameService gameService, GamePlatformService gamePlatformService) {
        this.gameService = gameService;
        this.gamePlatformService = gamePlatformService;
    }


    /**
     * Display all games sorted by release year reversed.
     *
     * @param model The Model object that add attributes.
     * @return The view name of game-listing.
     */
    @GetMapping("/")
    public String home(Model model) {
        addModelAttributes(model);
        return"game-listing";
    }

    private void addModelAttributes(Model model) {
        List<GameDto> allGames = gameService.findAllGames()
                .stream()
                .sorted(Comparator.comparing(GameDto::getReleaseYear).reversed())
                .toList();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();
        model.addAttribute("platforms", gamePlatforms);
        model.addAttribute("heading", "Wielka encyklopedia gier");
        model.addAttribute("description", "Encyklopedia Gier GameZone.pl zawiera opisy, screeny," +
                " trailery, daty premier, wymagania sprzętowe, oceny i recenzje gier zarówno przed, jak i po premierze. Wszystkie gry w jednym miejscu!");
        model.addAttribute("games", allGames);
        model.addAttribute("allPlatforms", "Wszystkie");

        GameSuggestionsDto gameByClosestPremierDate = gameService
                .findGameByClosestPremierDate().orElse(null);
        model.addAttribute("closestGameReleaseDate", gameByClosestPremierDate);
    }


}

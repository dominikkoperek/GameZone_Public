package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

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
     * @param model The Model object that add attributes.
     * @return The view name of game-listing.
     */
    @GetMapping("/")
    public String home(Model model) {
        List<GameDto> allGames = gameService.findAllGames()
                .stream()
                .sorted(Comparator.comparing(GameDto::getReleaseYear).reversed())
                .toList();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();

        model.addAttribute("platforms", gamePlatforms);
        model.addAttribute("heading", "Wielka encyklopedia gier");
        model.addAttribute("description", "Encyklopedia zawiera opisy, trailery, daty premier, " +
                " oceny i recenzje gier zarówno przed i po premierze!");
        model.addAttribute("games", allGames);
        model.addAttribute("allPlatforms", "Wszystkie");
        return "game-listing";
    }
}

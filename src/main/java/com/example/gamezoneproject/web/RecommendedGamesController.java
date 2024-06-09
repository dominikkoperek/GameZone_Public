package com.example.gamezoneproject.web;

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
 * Controller for recommended games.
 */
@Controller
public class RecommendedGamesController {
    private final GameService gameService;
    private final GamePlatformService gamePlatformService;

    public RecommendedGamesController(GameService gameService, GamePlatformService gamePlatformService) {
        this.gameService = gameService;
        this.gamePlatformService = gamePlatformService;
    }

    /**
     * Display list of the all recommended games.
     * @param model The Model object that add attributes.
     * @return  The view name of game-listing.
     */

    @GetMapping("/polecane-gry")
    public String home(Model model) {
        List<GameDto> promotedGames = gameService.findAllPromotedGames()
                .stream()
                .toList();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();

        model.addAttribute("platforms",gamePlatforms);
        model.addAttribute("heading", "Wyróżnione gry");
        model.addAttribute("description", "Gry rekomendowane przez nasz zespół");
        model.addAttribute("allPlatforms", "Wszystkie");
        model.addAttribute("games", promotedGames);
        return "game-listing";
    }
}

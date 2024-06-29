package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Controller for recommended games.
 */
@Controller
@RequestMapping("/gry")

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

    @GetMapping("/top-gry")
    public String home(Model model) {
        List<GameDto> promotedGames = gameService.findAllPromotedGames()
                .stream()
                .toList();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();

        model.addAttribute("platforms",gamePlatforms);
        model.addAttribute("heading", "Top gry");
        model.addAttribute("description", " TOP Gry to sekcja, w której znajdziesz zestawienie najpopularniejszych i najlepiej ocenianych gier. To miejsce, w którym gracze mogą dowiedzieć się, które tytuły są obecnie na topie i zyskują największą popularność.");
        model.addAttribute("allPlatforms", "Wszystkie");
        model.addAttribute("games", promotedGames);
        model.addAttribute("games", promotedGames);
        model.addAttribute("sectionDescription","Top gry");
        model.addAttribute("displayGameListNav",true);

        return "game-listing";
    }
}

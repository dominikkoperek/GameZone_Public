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
                .sorted(Comparator.comparing(GameDto::getReleaseYear).reversed())
                .toList();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();

        model.addAttribute("platforms",gamePlatforms);
        model.addAttribute("heading", "Wyróżnione gry");
        model.addAttribute("description", "Gry rekomendowane przez nasz zespół");
        model.addAttribute("allPlatforms", "Wszystkie");
        model.addAttribute("games", promotedGames);
        model.addAttribute("recommendationHeading","Polecane dla Ciebie!");
        model.addAttribute("recommendationDescription","Nasz serwis dostosowuje rekomendacje do Twoich preferencji, bazując na Twoimzachowaniu na stronie oraz ocenach gier. Jeśli często przeglądasz gry z kategorii “romans”, to w naszychrekomendacjachznajdziesz więcej takich tytułów. Dodatkowo, jeśli wysoko oceniłeś gry akcji, zwiększymy szanse napojawienie się podobnych gier w Twoich polecanych. Dzięki temu, rekomendacje są jak najbardziej dopasowane doTwoich gustów.");
        return "game-listing";
    }
}

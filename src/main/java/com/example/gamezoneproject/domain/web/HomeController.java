package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class HomeController {
    private final GameService gameService;
    private final GamePlatformService gamePlatformService;

    public HomeController(GameService gameService, GamePlatformService gamePlatformService) {
        this.gameService = gameService;
        this.gamePlatformService = gamePlatformService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<GameDto> allGames = gameService.findAllGames();
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

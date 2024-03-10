package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class GamePlatformController {
    private final GamePlatformService gamePlatformService;
    private final GameService gameService;

    public GamePlatformController(GamePlatformService gamePlatformService, GameService gameService) {
        this.gamePlatformService = gamePlatformService;
        this.gameService = gameService;
    }

    @GetMapping("/gry/platforma/{name}")
    public String getCategory(@PathVariable String name, Model model) {
        GamePlatformDto gamePlatform = gamePlatformService.findGamePlatformByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<GameDto> games = gameService.findGamesByGamePlatformName(name);
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();
        model.addAttribute("platforms",gamePlatforms);
        model.addAttribute("heading","Gry na " +gamePlatform.getName());
        model.addAttribute("description",gamePlatform.getDescription());
        model.addAttribute("allPlatforms", "Wszystkie");
        model.addAttribute("games",games);

        return "game-listing";
    }
}

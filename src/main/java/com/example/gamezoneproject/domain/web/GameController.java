package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/gry/{gameTitle}/{id}")
    public String game(@PathVariable Long id,
                       @PathVariable String gameTitle,
                       Model model) {
        GameDto gameDto = gameService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (gameTitle.replaceAll("-", " ").equalsIgnoreCase(gameDto.getTitle())) {
            model.addAttribute("game", gameDto);
            model.addAttribute("gameTitle", gameDto.getTitle());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "game";
    }
}

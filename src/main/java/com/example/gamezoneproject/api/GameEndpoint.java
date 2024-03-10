package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameEndpoint {
    private final GameService gameService;

    public GameEndpoint(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/company")
    public ResponseEntity<?> findAllGamesByCompanyId(@RequestParam(required = false) Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        List<GameByCompanyDto> games = gameService.findAllPromotedGamesByProducerId(id);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/allGames")
    public ResponseEntity<?> findAllGames() {
        List<String> games = gameService
                .findAllGames()
                .stream()
                .map(GameDto::getTitle)
                .toList();
        return ResponseEntity.ok(games);
    }
}

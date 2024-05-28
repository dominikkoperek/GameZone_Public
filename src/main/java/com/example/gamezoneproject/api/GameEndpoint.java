package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Endpoint controller responsible for getting info about games from database.
 */
@RestController
@RequestMapping("/api/games")
public class GameEndpoint {
    private final GameService gameService;

    public GameEndpoint(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Endpoint that finds all games in database by a company's id. If the id parameter is not provided, it returns a bad request code.
     *
     * @param id Optional id of the company.
     * @return ResponseEntity containing a list of games produced by the company with the provided id. If the id is not
     * provided, it returns a ResponseEntity with a bad request code.
     */
    @GetMapping("/company")
    public ResponseEntity<?> findAllGamesByCompanyId(@RequestParam(required = false) Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        List<GameByCompanyDto> games = gameService.findAllPromotedGamesByProducerId(id);
        return ResponseEntity.ok(games);
    }

    /**
     * Endpoint that finds all games in database and map them to get their names.
     *
     * @return ResponseEntity containing list of game's names.
     */
    @GetMapping("/allGames")
    public ResponseEntity<List<String>> findAllGames() {
        List<String> games = gameService
                .findAllGamesSortedByOldestReleaseDate()
                .stream()
                .map(GameDto::getTitle)
                .toList();
        return ResponseEntity.ok(games);
    }

    /**
     * Endpoint that check if game already exists in database by provided title
     * If the title param is null, it returns all games.
     *
     * @param title name of the game provided by user (optional).
     * @return ResponseEntity containing true/false if the title is provided, or a ResponseEntity containing list
     * of all games if the name is null.
     */
    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@RequestParam(required = false) String title) {
        boolean isGameAvailable = gameService.isTitleAvailable(title);
        if (title == null) {
            return findAllGames();
        }
        return ResponseEntity.ok(isGameAvailable);

    }
}

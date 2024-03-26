package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Endpoint controller responsible for getting info about game's platforms  from database
 */
@RestController
@RequestMapping("/api/platform")
public class GamePlatformEndpoint {
    private final GamePlatformService gamePlatformService;

    public GamePlatformEndpoint(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    /**
     * Endpoint that check if game platform already exists in database by provided platform param.
     * If the platform param  is null, it returns all categories.
     *
     * @param platform name of the game platform provided by user (optional).
     * @return ResponseEntity containing true/false if the platform param is provided, or a ResponseEntity containing list
     * of all platforms if the platform param is null.
     */
    @RequestMapping("/availability")
    public ResponseEntity<?> checkPlatformAvailability(@RequestParam(required = false) String platform) {
        boolean isPlatformAvailable = gamePlatformService.isGamePlatformAvailable(platform);
        if (platform == null) {
            return findAllPlatforms();
        } else {
            return ResponseEntity.ok(isPlatformAvailable);
        }
    }

    /**
     * Endpoint that finds all game's platforms in database and map them to get their names.
     *
     * @return ResponseEntity containing list of game's platforms names.
     */
    @RequestMapping("/allPlatforms")
    public ResponseEntity<List<String>> findAllPlatforms() {
        List<String> strings = gamePlatformService.findAllGamePlatforms()
                .keySet()
                .stream()
                .toList();
        return ResponseEntity.ok(strings);
    }
}

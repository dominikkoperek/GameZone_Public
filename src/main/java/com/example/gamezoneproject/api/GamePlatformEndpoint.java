package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/platform")
public class GamePlatformEndpoint {
    private final GamePlatformService gamePlatformService;

    public GamePlatformEndpoint(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    @RequestMapping("/availability")
    public ResponseEntity<?> checkPlatformAvailability(@RequestParam(required = false) String platform) {
        boolean isPlatformAvailable = gamePlatformService.isGamePlatformAvailable(platform);
        if (platform == null) {
            return findAllPlatforms();
        } else {
            return ResponseEntity.ok(isPlatformAvailable);
        }
    }

    @RequestMapping("/allPlatforms")
    public ResponseEntity<?> findAllPlatforms() {
        List<String> strings = gamePlatformService.findAllGamePlatforms()
                .keySet()
                .stream()
                .toList();
        return ResponseEntity.ok(strings);
    }
}

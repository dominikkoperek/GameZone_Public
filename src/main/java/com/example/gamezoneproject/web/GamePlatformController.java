package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformBrandDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * Controller for displaying content by platform
 */
@Controller
@RequestMapping("/gry")
public class GamePlatformController {
    private final GamePlatformService gamePlatformService;
    private final GameService gameService;

    public GamePlatformController(GamePlatformService gamePlatformService, GameService gameService) {
        this.gamePlatformService = gamePlatformService;
        this.gameService = gameService;
    }

    /**
     * Display all content by platform
     *
     * @param name  The name of the platform.
     * @param model The Model object that add attributes.
     * @return The view name of the game-listing.
     */
    @GetMapping("/platforma/{name}")
    public String getCategory(@PathVariable String name, Model model,
                              @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                      defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                              @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                      defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize) {

        if (pageNo <= 0 || pageSize <= 0) {
            return "redirect:/gry/platforma/" + name;
        }
        final String uri = "/gry/platforma/" + name + GlobalControllerAdvice.PAGE_PATH_QUERY;
        GamePlatformDto gamePlatformDto = gamePlatformService.findGamePlatformByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int nextPage = pageNo + 1;
        int previousPage = Math.max(pageNo - 1,1);
        GamePageDto allGames = gameService.findGamesByGamePlatformName(name, pageNo, pageSize);
        int totalPages = allGames.getTotalPages();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();
        addToModel(model, gamePlatforms, gamePlatformDto, allGames, pageNo, totalPages, nextPage, previousPage, uri);
        if (pageNo > totalPages) {
            return buildUri(totalPages, name);
        }
        return "game-listing";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:/gry";
    }


    private static String buildUri(Integer totalPages, String name) {
        return UriComponentsBuilder
                .fromPath("redirect:/")
                .path("gry/platforma/")
                .path(name)
                .queryParam(GlobalControllerAdvice.PAGE_PATH_VARIABLE, totalPages)
                .build()
                .toString();
    }

    private static void addToModel(Model model, LinkedHashMap<String, String> gamePlatforms, GamePlatformDto gamePlatform,
                                   GamePageDto games, int currentPage, int totalPages, int nextPage, int prevPage, String uri) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("platforms", gamePlatforms);
        model.addAttribute("nextPageLink", uri);
        model.addAttribute("heading", "Gry na " + gamePlatform.getName());
        model.addAttribute("description", gamePlatform.getDescription());
        model.addAttribute("content", games);
        model.addAttribute("sectionDescription", "Gry na " + gamePlatform.getName());
        model.addAttribute("displayGameListNav", true);
    }
}

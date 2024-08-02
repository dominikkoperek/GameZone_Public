package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Controller for recommended content.
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
     * Display list of the all recommended content.
     *
     * @param model The Model object that add attributes.
     * @return The view name of game-listing.
     */

    @GetMapping({"/top-gry", "/top-gry/"})
    public String home(Model model,
                       @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                       @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                               defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize) {
        if (pageNo <= 0 || pageSize <= 0) {
            return "redirect:/gry/top-gry";
        }
        int nextPage = pageNo + 1;
        int previousPage = Math.max(pageNo - 1,1);
        GamePageDto allPromotedGames = gameService.findAllPromotedGames(pageNo, pageSize);
        int totalPages = allPromotedGames.getTotalPages();
        LinkedHashMap<String, String> gamePlatforms = gamePlatformService.findAllGamePlatforms();
        addToModel(model, gamePlatforms, allPromotedGames, pageNo, totalPages, nextPage, previousPage);

        if (pageNo > totalPages) {
            return "redirect:/gry/top-gry"+ GlobalControllerAdvice.PAGE_PATH_QUERY + totalPages;
        }
        return "game-listing";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:redirect:/gry/top-gry";
    }

    private static void addToModel(Model model, LinkedHashMap<String, String> gamePlatforms, GamePageDto promotedGames,
                                   int currentPage, int totalPages, int nextPage, int prevPage) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPageLink", "/gry/top-gry" + GlobalControllerAdvice.PAGE_PATH_QUERY);
        model.addAttribute("platforms", gamePlatforms);
        model.addAttribute("heading", "Top gry");
        model.addAttribute("description", " TOP Gry to sekcja, w której znajdziesz zestawienie najpopularniejszych i najlepiej ocenianych gier. To miejsce, w którym gracze mogą dowiedzieć się, które tytuły są obecnie na topie i zyskują największą popularność.");
        model.addAttribute("allPlatforms", "Wszystkie");
        model.addAttribute("content", promotedGames);
        model.addAttribute("sectionDescription", "Top gry");
        model.addAttribute("displayGameListNav", true);
    }
}

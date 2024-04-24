package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.GameModeService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for game add form.
 */
@Controller
public class GameManagementController {
    private final GameService gameService;
    private final GameModeService gameModeService;
    private final GamePlatformService gamePlatformService;
    private final CategoryService categoryService;

    public GameManagementController(GameService gameService, GameModeService gameModeService,
                                    GamePlatformService gamePlatformService, CategoryService categoryService) {
        this.gameService = gameService;
        this.gameModeService = gameModeService;
        this.gamePlatformService = gamePlatformService;
        this.categoryService = categoryService;
    }

    /**
     * Binds a form with a GameSaveDto object. It uses game mode service,game platform service and category service
     * and adding attributes to form.
     *
     * @param model The Model object that add attributes(GameModeDto,allGamePlatformsName, CategoryDto and GameSaveDto)
     * @return The view name of the game addition form.
     */
    @GetMapping("/admin/dodaj-gre")
    public String addGameForm(Model model) {
        GameSaveDto game = new GameSaveDto();
        model.addAttribute("game", game);
        addAttributesToModel(model);
        setMinAndMaxPlayers(game);
        return "admin/game-add-form";
    }


    /**
     * Handles the form submission for adding a game. Adds a new game to the database based on the provided DTO.
     *
     * @param gameSaveDto        The DTO of the game to be added. This should be validated properly.
     * @param bindingResult      The result of the validation of the game DTO.
     * @param redirectAttributes The redirect attributes used for passing a success message after the game is added.
     * @return If the form has errors, add again attributes and returns the view name of the game addition form,
     * otherwise redirects to the admin home page.
     */

    @PostMapping("/admin/dodaj-gre")
    public String addGame(@Valid @ModelAttribute("game") GameSaveDto gameSaveDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            addAttributesToModel(model);
            return "admin/game-add-form";
        } else {
            gameService.addGame(gameSaveDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Gra %s została dodana".formatted(gameSaveDto.getTitle()));
            return "redirect:/admin";
        }
    }

    private static void setMinAndMaxPlayers(GameSaveDto game) {
        PlayerRange playerRange = new PlayerRange();
        playerRange.setMinPlayers(1);
        playerRange.setMaxPlayers(1);
        game.setPlayerRange(playerRange);
    }
    private void addAttributesToModel(Model model) {
        List<GameModeDto> allGameModes = gameModeService.findAllGameModes();
        List<CategoryDto> categories = categoryService.findAllGameCategories();
        List<String> allAvailableGamePlatformsNames = gamePlatformService.findAllAvailableGamePlatformsNames();
        model.addAttribute("allGameModes", allGameModes);
        model.addAttribute("allCategories", categories);
        model.addAttribute("allAvailableGamePlatforms", allAvailableGamePlatformsNames);
    }
}

package com.example.gamezoneproject.domain.web.admin;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameModeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GameManagementController {
    private final GameService gameService;
    private final GameModeService gameModeService;

    public GameManagementController(GameService gameService, GameModeService gameModeService) {
        this.gameService = gameService;
        this.gameModeService = gameModeService;
    }
    @GetMapping("/admin/dodaj-gre")
    public String addGameForm(Model model){
        GameSaveDto game = new GameSaveDto();
        List<GameModeDto> allGameModes = gameModeService.findAllGameModes();

        model.addAttribute("allGameModes",allGameModes);
        model.addAttribute("game",game);
        return "admin/game-add-form";
    }
    @PostMapping("/admin/dodaj-gre")
    public String addGame(@Valid @ModelAttribute("game") GameSaveDto game,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "admin/game-add-form";
        } else {
            gameService.addGame(game);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Gra %s została dodana".formatted(game.getTitle()));
            return "redirect:/admin";
        }
    }
}

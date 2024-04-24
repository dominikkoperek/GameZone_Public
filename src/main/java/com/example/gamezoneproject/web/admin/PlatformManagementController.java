package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import com.example.gamezoneproject.domain.game.gameDetails.platform.dto.GamePlatformDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;

/**
 *  Controller for game platform add form.
 */
@Controller
public class PlatformManagementController {
    private final GamePlatformService platformService;
    private final GamePlatformService gamePlatformService;


    public PlatformManagementController(GamePlatformService platformService,
                                        GamePlatformService gamePlatformService) {
        this.platformService = platformService;
        this.gamePlatformService = gamePlatformService;
    }

    /**
     * Binds a form with a GamePlatformDto object. It uses game platform service for adding attributes to form.
     * @param model The Model object that add attributes GamePlatformDto, allGamePlatforms.
     * @return The view name of the platform addition form.
     */
    @GetMapping("/admin/dodaj-platforme")
    public String addPlatformForm(Model model) {
        model.addAttribute("platform", new GamePlatformDto());
        addModelAttributes(model);
        return "admin/platform-add-form";
    }

    private void addModelAttributes(Model model) {
        LinkedHashMap<String, String> allGamePlatforms = gamePlatformService.findAllGamePlatforms();
        model.addAttribute("allGamePlatforms", allGamePlatforms);
    }

    /**
     * Handles the form submission for adding a game platform. Adds a new game platform to the database based on the provided DTO.
     * @param gamePlatformDto The DTO of the game platform to be added. This should be validated properly.
     * @param bindingResult The result of the validation of the game platform DTO.
     * @param redirectAttributes The redirect attributes used for passing a success message after the game platform is added.
     * @return If the form has errors, returns the view name of the game platform addition form, otherwise redirects to the admin home page.
     */
    @PostMapping("/admin/dodaj-platforme")
    public String addPlatform(@Valid @ModelAttribute("platform") GamePlatformDto gamePlatformDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            addModelAttributes(model);
            return "admin/platform-add-form";
        } else {
            platformService.addGamePlatform(gamePlatformDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Platforma %s została dodana".formatted(gamePlatformDto.getName()));
            return "redirect:/admin";
        }
    }
}

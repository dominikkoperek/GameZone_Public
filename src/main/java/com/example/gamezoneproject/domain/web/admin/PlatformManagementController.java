package com.example.gamezoneproject.domain.web.admin;

import com.example.gamezoneproject.domain.exceptions.PlatformNotFoundException;
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
import java.util.Optional;

@Controller
public class PlatformManagementController {
    private final GamePlatformService platformService;
    private final GamePlatformService gamePlatformService;
    private static final String PLATFORM_ALL_NAME = "wszystkie";


    public PlatformManagementController(GamePlatformService platformService,
                                        GamePlatformService gamePlatformService) {
        this.platformService = platformService;
        this.gamePlatformService = gamePlatformService;
    }

    @GetMapping("/admin/dodaj-platforme")
    public String addPlatformForm(Model model) {
        model.addAttribute("platform", new GamePlatformDto());
        LinkedHashMap<String, String> allGamePlatforms = gamePlatformService.findAllGamePlatforms();
        model.addAttribute("allGamePlatforms", allGamePlatforms);

        return "admin/platform-add-form";
    }

    @PostMapping("/admin/dodaj-platforme")
    public String addPlatform(@Valid @ModelAttribute("platform") GamePlatformDto gamePlatformDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/platform-add-form";
        } else {
            platformService.addGamePlatform(gamePlatformDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Platforma %s została dodana".formatted(gamePlatformDto.getName()));
            return "redirect:/admin";
        }
    }
}

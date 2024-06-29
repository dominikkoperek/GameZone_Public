package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameSuggestionsDto;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatformService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 * Home controller for all games.
 */
@Controller
public class HomeController {


    /**
     * Display all games sorted by release year reversed.
     *
     * @param model The Model object that add attributes.
     * @return The view name of game-listing.
     */
    @GetMapping("/")
    public String home(Model model) {
        addModelAttributes(model);
        return "redirect:/gry";
    }

    private void addModelAttributes(Model model) {
    }
}

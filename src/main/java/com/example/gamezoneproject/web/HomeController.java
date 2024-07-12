package com.example.gamezoneproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller for all content.
 */
@Controller
public class HomeController {


    /**
     * Display all content sorted by release year reversed.
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

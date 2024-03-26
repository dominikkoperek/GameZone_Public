package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for category list and game list sorted by categories.
 */
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final GameService gameService;

    public CategoryController(CategoryService categoryService, GameService gameService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
    }

    /**
     * Method responsible displaying list of games by category name. If category name not exists throw ResponseStatusException
     *
     * @param name  The name of the category.
     * @param model The Model object that add attribute GameDto and two messages category name and description.
     * @return The view name of the game listing.
     */
    @GetMapping("/gry/kategorie/{name}")
    public String getCategory(@PathVariable String name, Model model) {
        CategoryDto category = categoryService.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GameDto> games = gameService.findGamesByCategoryName(name);
        model.addAttribute("heading", category.getName());
        model.addAttribute("description", category.getDescription());
        model.addAttribute("games", games);
        return "game-listing";
    }

    /**
     *  Method responsible displaying list of all categories. It sorts the names of categories by their names.
     * @param model The Model object that add attributes, CategoryDto and messages, heading and description.
     * @return The view name of the categories-listing.
     */
    @GetMapping("/gry/kategoria")
    public String getCategoryList(Model model) {
        List<CategoryDto> categories = categoryService.findAllGameCategories()
                .stream()
                .sorted(Comparator.comparing(category -> category.getName().toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("heading", "Kategorie gier");
        model.addAttribute("description", " Każda gra ma jeden główny gatunek: Akcji, Bijatyki, Logiczne, Przygodowe, RPG, Sportowe, Strategiczne, Symulacje, Romanse, Wyścigi, Zręcznościowe itp. Dodatkowo każda gra ma kilka pobocznych kategorii dzięki którym znajdziesz swój ulubiony tytuł!");
        return "categories-listing";
    }
}

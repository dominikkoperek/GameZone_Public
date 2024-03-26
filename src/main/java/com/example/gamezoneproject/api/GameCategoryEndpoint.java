package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint controller responsible for getting info about game's categories from database
 */
@RestController
@RequestMapping("/api/category")
public class GameCategoryEndpoint {
    private final CategoryService categoryService;

    public GameCategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpoint that check if game category already exists in database by provided category param.
     * If the category param  is null, it returns all categories.
     *
     * @param category name of the game category provided by user (optional).
     * @return ResponseEntity containing true/false if the category param is provided, or a ResponseEntity containing list
     * of all categories if the category param is null.
     */
    @GetMapping("/availability")
    public ResponseEntity<?> checkCategoryAvailability(@RequestParam(required = false) String category) {
        boolean isCategoryAvailable = categoryService.isCategoryAvailable(category);
        if (category == null) {
            return findAllCategories();
        }
        return ResponseEntity.ok(isCategoryAvailable);
    }

    /**
     * Endpoint that finds all game's categories in database and map them to get their names.
     *
     * @return ResponseEntity containing list of game's categories names.
     */
    @GetMapping("/allCategories")
    public ResponseEntity<List<String>> findAllCategories() {
        List<String> categories = categoryService
                .findAllGameCategories()
                .stream()
                .map(CategoryDto::getName)
                .toList();
        return ResponseEntity.ok(categories);
    }
}



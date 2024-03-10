package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/category")
public class GameCategoryEndpoint {
    private final CategoryService categoryService;

    public GameCategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/availability")
    public ResponseEntity<?> checkCategoryAvailability(@RequestParam(required = false) String category) {
        boolean isCategoryAvailable = categoryService.isCategoryAvailable(category);
        if (category == null) {
            return findAllCategories();
        }
        return ResponseEntity.ok(isCategoryAvailable);
    }

    @GetMapping("/allCategories")
    public ResponseEntity<?> findAllCategories() {
        List<String> categories = categoryService
                .findAllGameCategories()
                .stream()
                .map(CategoryDto::getName)
                .toList();
        return ResponseEntity.ok(categories);
    }
}



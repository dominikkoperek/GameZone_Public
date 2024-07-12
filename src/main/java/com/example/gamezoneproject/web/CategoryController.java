package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.GameSuggestionsDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import com.example.gamezoneproject.web.global.GlobalControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for category list and game list sorted by categories.
 */
@Controller
@RequestMapping("/gry")

public class CategoryController {
    private final CategoryService categoryService;
    private final GameService gameService;

    public CategoryController(CategoryService categoryService, GameService gameService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
    }

    /**
     * Method responsible displaying list of content by category name. If category name not exists throw ResponseStatusException
     *
     * @param name  The name of the category.
     * @param model The Model object that add attribute GameDto and two messages category name and description.
     * @return The view name of the game listing.
     */
    @GetMapping("/kategorie/{name}")
    public String getCategory(@PathVariable String name, Model model,
                              @RequestParam(value = GlobalControllerAdvice.PAGE_PATH_VARIABLE,
                                      defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_NUMBER) Integer pageNo,
                              @RequestParam(value = GlobalControllerAdvice.PAGE_SIZE_VARIABLE,
                                      defaultValue = GlobalControllerAdvice.DEFAULT_PAGE_SIZE) Integer pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            return "redirect:/gry/kategorie/"+name;
        }
        final String uri = "/gry/kategorie/" + name + GlobalControllerAdvice.PAGE_PATH_QUERY;
        int nextPage = pageNo + 1;
        int previousPage = pageNo - 1;
        CategoryDto category = categoryService.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        GamePageDto allGames = gameService.findGamesByCategoryName(name,pageNo,pageSize);
        int totalPages = Math.max((allGames.getTotalPages() - 1), 0);
        addToModel(model, category, allGames, pageNo, totalPages, nextPage, previousPage,uri);
        if (pageNo > totalPages) {
            return buildUri(totalPages, name);
        }
        return "game-listing";
    }

    private String buildUri(int totalPages, String name) {
        return UriComponentsBuilder
                .fromPath("redirect:/")
                .path("gry/kategorie/")
                .path(name)
                .queryParam(GlobalControllerAdvice.PAGE_PATH_VARIABLE, totalPages)
                .build()
                .toString();
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "redirect:/gry";
    }

    private static void addToModel(Model model, CategoryDto category, GamePageDto games, int currentPage,
                                   int totalPages, int nextPage, int prevPage,String uri) {
        model.addAttribute("nextPageLink", uri);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("heading", category.getName());
        model.addAttribute("description", category.getDescription());
        model.addAttribute("content", games);
        model.addAttribute("recommendationHeading", "Polecane dla Ciebie!");
        model.addAttribute("recommendationDescription", "Nasz serwis dostosowuje rekomendacje do Twoich preferencji, bazując na Twoimzachowaniu na stronie oraz ocenach gier. Jeśli często przeglądasz gry z kategorii “romans”, to w naszychrekomendacjachznajdziesz więcej takich tytułów. Dodatkowo, jeśli wysoko oceniłeś gry akcji, zwiększymy szanse napojawienie się podobnych gier w Twoich polecanych. Dzięki temu, rekomendacje są jak najbardziej dopasowane doTwoich gustów.");
        model.addAttribute("closestPremierGame", new GameSuggestionsDto());
        model.addAttribute("sectionDescription", "Kategorie gier");
        model.addAttribute("displayGameListNav", true);
    }

    /**
     * Method responsible displaying list of all categories. It sorts the names of categories by their names.
     *
     * @param model The Model object that add attributes, CategoryDto and messages, heading and description.
     * @return The view name of the categories-listing.
     */
    @GetMapping("/kategorie")
    public String getCategoryList(Model model) {
        List<CategoryDto> categories = categoryService.findAllGameCategories()
                .stream()
                .sorted(Comparator.comparing(category -> category.getName().toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("heading", "Kategorie gier");
        model.addAttribute("description", " Każda gra ma jeden główny gatunek: Akcji, Bijatyki, Logiczne, Przygodowe, RPG, Sportowe, Strategiczne, Symulacje, Romanse, Wyścigi, Zręcznościowe itp. Dodatkowo każda gra ma kilka pobocznych kategorii dzięki którym znajdziesz swój ulubiony tytuł!");
        model.addAttribute("sectionDescription", "Kategorie gier");
        model.addAttribute("displayGameListNav", true);
        return "categories-listing";
    }
}

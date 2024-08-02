package com.example.gamezoneproject.domain.game.gameDetails.category;

import com.example.gamezoneproject.domain.game.dto.GameDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class shares public methods which allow to search, add, and check availability of game's categories.
 * It uses the CategoryRepository to interact with the database.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * This method is using the repository, finding a game category by the provided name,
     * and then mapping the result to a CategoryDto.
     *
     * @param name The name of the game category provided by the user.
     * @return An Optional containing a CategoryDto if a category with the provided name is found, or an empty Optional
     * if no category is found.
     */
    public Optional<CategoryDto> findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .map(CategoryDtoMapper::map);
    }

    /**
     * This method uses the repository to find all game categories, maps the result to CategoryDto, and sorts
     * the results by name in lower case.
     *
     * @return A list of all game categories DTOs sorted alphabetically by name.
     */
    public List<CategoryDto> findAllGameCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(CategoryDtoMapper::map)
                .sorted(Comparator.comparing(categoryDto -> categoryDto.getName().toLowerCase()))
                .toList();
    }

    /**
     * This method uses the repository to save a new category in the database. It uses a CategoryDto to get all
     * the necessary information, and bind them to entity.
     * The CategoryDto should contain the name and description of the category to be added.
     *
     * @param categoryDto Object containing the name and description of the new category.
     */
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category categoryToSave = new Category();
        categoryToSave.setName(categoryDto.getName().trim());
        categoryToSave.setDescription(categoryDto.getDescription().trim());
        categoryRepository.save(categoryToSave);
    }

    /**
     * This method uses the repository to check if a category is available by the provided name.
     * It checks if a category with the provided name already exists in the database.
     *
     * @param name The name of the category provided by the user.
     * @return True if the provided name does not exist in the database, and false if the category already exists in the database.
     */
    public boolean isCategoryAvailable(String name) {
        return categoryRepository
                .findByNameIgnoreCase(name)
                .isEmpty();
    }

}

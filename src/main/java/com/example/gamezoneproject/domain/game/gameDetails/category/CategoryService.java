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

    public boolean isCategoryAvailable(String name) {
        return categoryRepository
                .findByNameIgnoreCase(name)
                .isEmpty();
    }
    public Set<String> getNormalizedCategories(List<String> cat) {
        return findAllGameCategories()
                .stream()
                .map(CategoryDto::getName)
                .filter(name-> cat.stream().anyMatch(name::equalsIgnoreCase))
                .collect(Collectors.toSet());
    }

}

package com.example.gamezoneproject.domain.game.gameDetails.category;

import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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

    public List<CategoryDto> findAllGameCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(CategoryDtoMapper::map)
                .toList();
    }

    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category categoryToSave = new Category();
        categoryToSave.setName(categoryDto.getName().trim());
        categoryToSave.setDescription(categoryDto.getDescription().trim());
        categoryRepository.save(categoryToSave);
    }

    public boolean isCategoryAvailable(String name) {
        return categoryRepository.findByNameIgnoreCase(name).isEmpty();
    }
}

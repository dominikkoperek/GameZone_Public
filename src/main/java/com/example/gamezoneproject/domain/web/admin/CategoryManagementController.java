package com.example.gamezoneproject.domain.web.admin;

import com.example.gamezoneproject.domain.game.gameDetails.category.CategoryService;
import com.example.gamezoneproject.domain.game.gameDetails.category.dto.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryManagementController {
    private final CategoryService categoryService;

    public CategoryManagementController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/dodaj-kategorie")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "admin/category-add-form";
    }

    @PostMapping("/admin/dodaj-kategorie")
    public String addCategory(@Valid @ModelAttribute("category")  CategoryDto categoryDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category-add-form";
        } else {
            categoryService.addCategory(categoryDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Gatunek %s został dodany".formatted(categoryDto.getName()));
            return "redirect:/admin";
        }
    }
}

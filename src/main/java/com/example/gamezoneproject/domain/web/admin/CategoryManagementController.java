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

/**
 * Controller for category add form.
 */
@Controller
public class CategoryManagementController {
    private final CategoryService categoryService;

    public CategoryManagementController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Binds a form with a CategoryDto object.
     *
     * @param model The Model object that will be populated with a new CategoryDto.
     * @return The view name of the category addition form.
     */
    @GetMapping("/admin/dodaj-kategorie")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "admin/category-add-form";
    }

    /**
     * Handles the form submission for adding a category. Adds a new category to the database based on the provided DTO.
     *
     * @param categoryDto The DTO of the category to be added. This should be validated properly.
     * @param bindingResult The result of the validation of the category DTO.
     * @param redirectAttributes The redirect attributes used for passing a success message after the category is added.
     * @return If the form has errors, returns the view name of the category addition form, otherwise redirects to the admin home page.
     */
    @PostMapping("/admin/dodaj-kategorie")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDto categoryDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
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

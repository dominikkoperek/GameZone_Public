package com.example.gamezoneproject.domain.web.admin;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import com.example.gamezoneproject.domain.game.gameDetails.country.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
/**
 * Controller for company add form.
 */
@Controller
public class CompanyManagementController {
    private final CompanyService companyService;

    public CompanyManagementController(CompanyService companyService) {
        this.companyService = companyService;
    }
    /**
     * Binds a form with a CompanyDto object.
     *
     * @param model The Model object that will be populated with a new CompanyDto.
     * @return The view name of the company addition form.
     */
    @GetMapping("/admin/dodaj-firme")
    public String addCompanyModel(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "admin/company-add-form";
    }

    /**
     * Handles the form submission for adding a company. Adds a new company to the database based on the provided DTO.
     * @param companyDto The DTO of the company to be added. This should be validated properly.
     * @param bindingResult The result of the validation of the company DTO.
     * @param redirectAttributes The redirect attributes used for passing a success message after the company is added.
     * @param isProducer Additional param used for passing information whether company is Producer.
     * @param isPublisher Additional param used for passing information whether company is Publisher.
     * @return If the form has errors, returns the view name of the company addition form, otherwise redirects to the admin home page.
     */
    @PostMapping("/admin/dodaj-firme")
    public String addCategory(@Valid @ModelAttribute("company") CompanyDto companyDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(required = false, defaultValue = "false") Boolean isProducer,
                              @RequestParam(required = false, defaultValue = "false") Boolean isPublisher
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/company-add-form";

        } else {
            setProducerAndPublisher(companyDto, isProducer, isPublisher);
            companyService.addCompany(companyDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Firma %s została dodana".formatted(companyDto.getName()));
            return "redirect:/admin";
        }
    }

    private static void setProducerAndPublisher(CompanyDto companyDto, Boolean isProducer, Boolean isPublisher) {
        companyDto.setProducer(isProducer);
        companyDto.setPublisher(isPublisher);
    }


}

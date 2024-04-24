package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanySaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.country.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for company add form.
 */
@Controller
public class CompanyManagementController {
    private final CompanyService companyService;
    private final CountryService countryService;

    public CompanyManagementController(CompanyService companyService, CountryService countryService) {
        this.companyService = companyService;
        this.countryService = countryService;
    }

    /**
     * Binds a form with a CompanySaveDto object.
     *
     * @param model The Model object that will be populated with a new CompanyDto.
     * @return The view name of the company addition form.
     */
    @GetMapping("/admin/dodaj-firme")
    public String addCompanyModel(Model model) {
        CompanySaveDto company = new CompanySaveDto();
        model.addAttribute("company", company);
        addCountriesToModel(model);

        return "admin/company-add-form";
    }


    private void addCountriesToModel(Model model) {
        List<String> allCountries = countryService.findAllCountries();
        model.addAttribute("allCountries", allCountries);
    }

    /**
     * Handles the form submission for adding a company. Adds a new company to the database based on the provided DTO.
     *
     * @param companySaveDto     The DTO of the company to be added. This should be validated properly.
     * @param bindingResult      The result of the validation of the companySaveDto.
     * @param redirectAttributes The redirect attributes used for passing a success message after the company is added.
     * @return If the form has errors, returns the view name of the company addition form, otherwise redirects to the admin home page.
     */
    @PostMapping("/admin/dodaj-firme")
    public String addCategory(@Valid @ModelAttribute("company") CompanySaveDto companySaveDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            addCountriesToModel(model);
            return "admin/company-add-form";

        } else {
            companyService.addCompany(companySaveDto);
            redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE,
                    "Firma %s została dodana".formatted(companySaveDto.getName()));
            return "redirect:/admin";
        }
    }


}

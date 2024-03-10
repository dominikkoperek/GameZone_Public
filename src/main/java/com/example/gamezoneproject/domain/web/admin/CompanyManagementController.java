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

@Controller
public class CompanyManagementController {
    private final CompanyService companyService;

    public CompanyManagementController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/admin/dodaj-firme")
    public String addCompanyModel(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "admin/company-add-form";
    }

    @PostMapping("/admin/dodaj-firme")
    public String addCategory(@Valid @ModelAttribute("company") CompanyDto companyDto,
                              BindingResult bindingResult,
                              Model model,
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
        if (isProducer) {
            companyDto.setProducer(true);
        }
        if (isPublisher) {
            companyDto.setPublisher(true);
        }
        if (!isProducer) {
            companyDto.setProducer(false);
        }
        if (!isPublisher) {
            companyDto.setPublisher(false);
        }
    }


}

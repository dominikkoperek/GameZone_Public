package com.example.gamezoneproject.domain.web;

import com.example.gamezoneproject.domain.game.GameService;
import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.gameDetails.company.CompanyService;
import com.example.gamezoneproject.domain.game.gameDetails.company.dto.CompanyDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class CompanyController {
    private final static String POLISH_NAME  = "Polska";
    private final CompanyService companyService;
    private final GameService gameService;

    public CompanyController(CompanyService companyService, GameService gameService) {
        this.companyService = companyService;
        this.gameService = gameService;
    }

    @GetMapping("/firma")
    public String getAllCompanies(Model model) {

        List<CompanyDto> allCompanies = companyService.findAllCompanies();
        List<CompanyDto> polishProducers = companyService.findAllPolishProducers();
        List<CompanyDto> polishPublishers = companyService.findAllPolishPublishers();
        List<CompanyDto> allPublishers = companyService.findAllPublishers();
        List<CompanyDto> allProducers = companyService.findAllProducers();

        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("polishProducers", polishProducers);
        model.addAttribute("polishPublishers", polishPublishers);
        model.addAttribute("allPublishers", allPublishers);
        model.addAttribute("allProducers", allProducers);

        model.addAttribute("heading", "Producenci i wydawcy gier komputerowych");
        model.addAttribute("description", "Lista światowych i polskich producentów (deweloperów)" +
                " oraz wydawców gier. Zawiera zarówno firmy, które do tej pory funkcjonują w branży gier komputerowych " +
                "i konsolowych, jak i nieistniejące już przedsiębiorstwa. Klikając na nazwę firmy, możesz zapoznać się " +
                "z jej portfolio, a w niektórych przypadkach także przeczytać o jej historii.");
        return "company-listing";
    }
    @GetMapping("/firma/{companyName}/{id}")
    public String company(@PathVariable Long id,
                          @PathVariable String companyName,
                          Model model) {
        CompanyDto companyDto = companyService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<GameByCompanyDto> allPromotedGamesByProducerId = gameService.findAllPromotedGamesByProducerId(id);
        List<GameByCompanyDto> allPromotedGamesByPublisherId = gameService.findAllPromotedGamesByPublisherId(id);
        List<GameByCompanyDto> allGamesByProducerId = gameService.findAllGamesByProducerId(id);
        List<GameByCompanyDto> allGamesByPublisherId = gameService.findAllGamesByPublisherId(id);
        boolean isPolishCompany = companyDto.getCountry().equalsIgnoreCase(POLISH_NAME);

        model.addAttribute("isPolishCompany",isPolishCompany);
        model.addAttribute("allPromotedGamesByProducerId",allPromotedGamesByProducerId);
        model.addAttribute("allPromotedGamesByPublisherId",allPromotedGamesByPublisherId);
        model.addAttribute("allGamesByProducerId",allGamesByProducerId);
        model.addAttribute("allGamesByPublisherId",allGamesByPublisherId);


        if (companyName.replaceAll("-", " ").equalsIgnoreCase(companyDto.getName())) {
            model.addAttribute("company", companyDto);
            model.addAttribute("heading", companyDto.getName());
            model.addAttribute("description", companyDto.getShortDescription());
            model.addAttribute("country", companyDto.getCountry());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "company";
    }


}

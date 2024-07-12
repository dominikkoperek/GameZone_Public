package com.example.gamezoneproject.web;

import com.example.gamezoneproject.domain.game.dto.GameByCompanyDto;
import com.example.gamezoneproject.domain.game.dto.PromotedGameByCompanyDto;
import com.example.gamezoneproject.domain.company.CompanyService;
import com.example.gamezoneproject.domain.company.dto.CompanyDto;
import com.example.gamezoneproject.domain.game.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Controller for companies list.
 */
@Controller
@RequestMapping("/katalog-firm")
public class CompanyController {
    private final static String POLISH_NAME = "Polska";
    private final CompanyService companyService;
    private final GameService gameService;

    public CompanyController(CompanyService companyService, GameService gameService) {
        this.companyService = companyService;
        this.gameService = gameService;
    }

    /**
     * Method for display all available companies from DB.
     *
     * @param model The Model object that add attributes.
     * @return The view name of company-listing.
     */
    @GetMapping("")
    public String getAllCompanies(Model model) {
        addModelAttributesAllCompanies(model);
        return "company-listing";
    }

    /**
     * Method for display all available polish producers companies from DB.
     *
     * @param model The Model object that add attributes.
     * @return The view name of company-listing.
     */
    @GetMapping("/firma/polscy-producenci")
    public String allPolishProducers(Model model) {
        addModelAttributesPolishProducersTitles(model);
        return "company-listing";
    }

    /**
     * Method for display all available polish publisher companies from DB.
     *
     * @param model The Model object that add attributes.
     * @return The view name of company-listing.
     */
    @GetMapping("/firma/polscy-wydawcy")
    public String allPolishPublishers(Model model) {
        addModelAttributesPolishPublisherTitles(model);
        return "company-listing";
    }

    @GetMapping("/firma/producenci/{letter}")
    public String allProducers(@PathVariable String letter, Model model) {
        if ((letter.length() != 1 && !letter.equals("0-9")) ||
                (letter.length() == 1 && !letter.matches("[A-Za-z]"))) {
            return "redirect:/katalog-firm/firma/producenci/top-producenci";
        }
        addModelAttributesAllProducers(model, letter);
        addModelAttributesAlphabet(model);
        return "company-listing";
    }

    @GetMapping("/firma/producenci/top-producenci")
    public String topProducers(Model model) {
        addModelAttributesTopProducers(model);
        addModelAttributesAlphabet(model);
        return "company-listing";
    }

    @GetMapping("/firma/wydawcy/{letter}")
    public String allPublishers(@PathVariable String letter, Model model) {
        if ((letter.length() != 1 && !letter.equals("0-9")) ||
                (letter.length() == 1 && !letter.matches("[A-Za-z]"))) {
            return "redirect:/katalog-firm/firma/wydawcy/top-wydawcy";
        }
        addModelAttributesAllPublishers(model, letter);
        addModelAttributesAlphabet(model);
        return "company-listing";
    }

    @GetMapping("/firma/wydawcy/top-wydawcy")
    public String topPublishers(Model model) {
        addModelAttributesTopPublishers(model);
        addModelAttributesAlphabet(model);
        return "company-listing";
    }


    /**
     * Display company info.
     *
     * @param id          The id of the company.
     * @param companyName Name of the company.
     * @param model       The Model object that add attributes.
     * @return The view name of company.
     */
    @GetMapping("/firma/{companyName}/{id}")
    public String company(@PathVariable Long id,
                          @PathVariable String companyName,
                          Model model) {
        addModelAttributesCompanyDetails(id, companyName, model);
        addModelAttributesCompaniesGamesLists(id, model);
        return "company";
    }


    private void addModelAttributesCompanyDetails(Long id, String companyName, Model model) {
        CompanyDto companyDto = companyService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (companyName.replaceAll("-", " ").equalsIgnoreCase(companyDto.getName())) {
            model.addAttribute("company", companyDto);
            model.addAttribute("heading", companyDto.getName());
            model.addAttribute("description", companyDto.getShortDescription());
            model.addAttribute("country", companyDto.getCountry());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void addModelAttributesCompaniesGamesLists(Long id, Model model) {
        CompanyDto companyDto = companyService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GameByCompanyDto> allPromotedGamesByProducerId = gameService
                .findAllPromotedGamesByProducerId(id);
        List<PromotedGameByCompanyDto> allPromotedGamesByPublisherId = gameService.findAllPromotedGamesByPublisherId(id);
        List<GameByCompanyDto> allGamesByProducerId = gameService.findAllGamesByProducerId(id);
        List<GameByCompanyDto> allGamesByPublisherId = gameService.findAllGamesByPublisherId(id);
        boolean isPolishCompany = companyDto.getCountry().equalsIgnoreCase(POLISH_NAME);

        model.addAttribute("isPolishCompany", isPolishCompany);
        model.addAttribute("allPromotedGamesByProducerId", allPromotedGamesByProducerId);
        model.addAttribute("allPromotedGamesByPublisherId", allPromotedGamesByPublisherId);
        model.addAttribute("allGamesByProducerId", allGamesByProducerId);
        model.addAttribute("allGamesByPublisherId", allGamesByPublisherId);
        model.addAttribute("sectionDescription", "Katalog firm");
        model.addAttribute("displayGameListNav", true);
    }

    private void addModelAttributesAllCompanies(Model model) {
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

        model.addAttribute("title", "Katalog firm - GameZone");
        model.addAttribute("heading", "Producenci i wydawcy gier komputerowych");
        model.addAttribute("description", "Lista światowych i polskich producentów (deweloperów)" +
                " oraz wydawców gier. Zawiera zarówno firmy, które do tej pory funkcjonują w branży gier komputerowych " +
                "i konsolowych, jak i nieistniejące już przedsiębiorstwa. Klikając na nazwę firmy, możesz zapoznać się " +
                "z jej portfolio, a w niektórych przypadkach także przeczytać o jej historii.");
        model.addAttribute("sectionDescription", "Katalog firm");
        model.addAttribute("displayGameListNav", true);

    }

    private void addModelAttributesPolishProducersTitles(Model model) {
        List<CompanyDto> allPolishProducers = companyService.findAllPolishProducers();
        model.addAttribute("heading", "Polscy producenci gier komputerowych");
        model.addAttribute("description", "Lista polskich producentów (deweloperów) gier komputerowych," +
                " konsolowych oraz na urządzenia mobilne. Zestawienie zawiera zarówno duże firmy produkujące tzw. tytuły AAA," +
                " jak i średnie oraz małe zespoły tworzące gry niezależne (indie).");
        model.addAttribute("polishProducers", allPolishProducers);
        model.addAttribute("displayPolishProducersFlag", true);
        model.addAttribute("title", "Polscy Producenci - GameZone");
        model.addAttribute("sectionDescription", "Katalog firm");
        model.addAttribute("displayGameListNav", true);
    }

    private void addModelAttributesPolishPublisherTitles(Model model) {
        List<CompanyDto> allPolishPublishers = companyService.findAllPolishPublishers();
        model.addAttribute("heading", "Polscy wydawcy gier komputerowych");
        model.addAttribute("description", "Lista polskich dystrybutorów i wydawców gier komputerowych," +
                " konsolowych oraz na urządzenia mobilne. Firmy te zwykle odpowiadają za dystrybucje do sklepów, a często także" +
                " za lokalizację (tłumaczenie) gry na język polski, marketing i pomoc techniczną. Zastawienie zawiera zarówno" +
                " podmioty funkcjonujące, jak i te, które zakończyły już swoją działalność. ");
        model.addAttribute("displayPolishPublishersFlag", true);
        model.addAttribute("title", "Polscy Wydawcy - GameZone");
        model.addAttribute("polishPublishers", allPolishPublishers);
        model.addAttribute("sectionDescription", "Katalog firm");
        model.addAttribute("displayGameListNav", true);
    }

    private void addModelAttributesAllProducers(Model model, String letter) {
        addInfoToProducer(model);

        if (letter.equals("0-9")) {
            List<CompanyDto> producersByNameDigit = companyService.findAllCompaniesByNameStartWithDigit()
                    .stream()
                    .filter(CompanyDto::getProducer)
                    .toList();
            model.addAttribute("producersByDigit", producersByNameDigit);
        } else {
            List<CompanyDto> producersByLetter = companyService.findAllCompaniesByFirstLetter(letter)
                    .stream()
                    .filter(CompanyDto::getProducer)
                    .toList();
            model.addAttribute("producersByLetter", producersByLetter);
        }
    }

    private void addModelAttributesTopProducers(Model model) {
        addInfoToProducer(model);
        List<CompanyDto> allProducers = companyService.findAllProducers();
        model.addAttribute("allProducers", allProducers);

    }

    private static void addInfoToProducer(Model model) {
        model.addAttribute("title", "Producenci - GameZone");
        model.addAttribute("displayProducerFlag", true);
        model.addAttribute("heading", "Producenci gier komputerowych");
        model.addAttribute("description", "Lista producentów (deweloperów) gier komputerowych," +
                " konsolowych oraz na urządzenia mobilne. Zestawienie „Top 100” najważniejszych i najpopularniejszych" +
                " deweloperów powstało na bazie popularności gier, które oni stworzyli. Po wybraniu danego producenta" +
                " możesz zobaczyć listę zaprojektowanych przez nich gier oraz serii, a także gry przed premierą będące" +
                " w fazie projektowania (o ile takowe zostały zapowiedziane lub dany zespół jeszcze istnieje).");
    }

    private void addModelAttributesAllPublishers(Model model, String letter) {
        addInfoToPublisher(model);
        if (letter.equals("0-9")) {
            List<CompanyDto> publishersByDigit = companyService.findAllCompaniesByNameStartWithDigit()
                    .stream()
                    .filter(CompanyDto::getPublisher)
                    .toList();
            model.addAttribute("publishersByDigit", publishersByDigit);
        } else {
            List<CompanyDto> publishersByLetter = companyService
                    .findAllCompaniesByFirstLetter(letter)
                    .stream()
                    .filter(CompanyDto::getPublisher)
                    .toList();
            model.addAttribute("publishersByLetter", publishersByLetter);
        }
    }

    private void addModelAttributesTopPublishers(Model model) {
        List<CompanyDto> allPublishers = companyService.findAllPublishers();
        addInfoToPublisher(model);
        model.addAttribute("allPublishers", allPublishers);
    }

    private static void addInfoToPublisher(Model model) {
        model.addAttribute("title", "Wydawcy - GameZone");
        model.addAttribute("displayPublisherFlag", true);
        model.addAttribute("heading", "Wydawcy gier komputerowych");
        model.addAttribute("description", "Lista wydawców gier komputerowych, konsolowych oraz " +
                "na urządzenia mobilne. Zestawienie „Top 100” najważniejszych i najpopularniejszych wydawców zostało opracowane" +
                " na podstawie sukcesu komercyjnego oraz wpływu na rynek gier, które wydali. Po wybraniu danego wydawcy," +
                " możesz zobaczyć listę gier, które zostały przez nich wydane, włączając w to zarówno serie gier, jak i" +
                " pojedyncze tytuły, a także nadchodzące produkcje, które są w trakcie przygotowania do premiery " +
                "(o ile zostały one oficjalnie ogłoszone i wydawca nadal funkcjonuje na rynku).");
    }


    private void addModelAttributesAlphabet(Model model) {
        List<Character> alphabet = IntStream
                .rangeClosed('A', 'Z')
                .mapToObj(c -> (char) c)
                .toList();
        model.addAttribute("alphabet", alphabet);
        model.addAttribute("sectionDescription", "Katalog firm");
        model.addAttribute("displayGameListNav", true);
    }
}

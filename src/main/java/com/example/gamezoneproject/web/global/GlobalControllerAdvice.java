package com.example.gamezoneproject.web.global;

import com.example.gamezoneproject.domain.game.GameDtoMapper;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    public static final String PAGE_PATH_VARIABLE = "page";
    public static final String PAGE_PATH_QUERY = "?page=";
    public static final String PAGE_SIZE_VARIABLE = "size";
    public static final String PAGE_SIZE_QUERY = "?size=";
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final String DEFAULT_PAGE_NUMBER = "1";

    @ModelAttribute
    public void addUserDetails(@CurrentSecurityContext SecurityContext securityContext, Model model) {
        model.addAttribute("username", securityContext.getAuthentication().getName());
        model.addAttribute("recommendationHeading", "Polecane dla Ciebie!");
        model.addAttribute("recommendationDescription", "Nasz serwis dostosowuje rekomendacje do Twoich preferencji, bazując na Twoimzachowaniu na stronie oraz ocenach gier. Jeśli często przeglądasz gry z kategorii “romans”, to w naszychrekomendacjachznajdziesz więcej takich tytułów. Dodatkowo, jeśli wysoko oceniłeś gry akcji, zwiększymy szanse napojawienie się podobnych gier w Twoich polecanych. Dzięki temu, rekomendacje są jak najbardziej dopasowane doTwoich gustów.");
        model.addAttribute("minVotesToDisplay", GameDtoMapper.MIN_VOTES_TO_CALCULATE_AVG_RATING);
    }

}

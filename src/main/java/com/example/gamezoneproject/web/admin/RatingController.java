package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.rating.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @PostMapping("/ocen-gre")
    public String addGameRating(@RequestParam long gameId,
                                @RequestParam double rating,
                                @RequestHeader String referer,
                                Authentication authentication) {
        String userName = authentication.getName();
        ratingService.addOrUpdateRating(userName, gameId, rating);
        return "redirect:" + referer;
    }
    @PostMapping("/usun-ocene")
    public String removeGameRating(@RequestParam long gameId,
                                   @RequestHeader String referer,
                                   Authentication authentication){
        String userName = authentication.getName();
        ratingService.removeRating(userName, gameId);
        return "redirect:" + referer;
    }
}

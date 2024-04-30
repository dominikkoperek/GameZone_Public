package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.user.UserService;
import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for account activation by temporary token.
 */
@Controller
@RequestMapping("/weryfikacja")
public class UserTokenController {
    private final UserService userService;
    private final TemporaryTokenService temporaryTokenService;

    public UserTokenController(UserService userService, TemporaryTokenService temporaryTokenService) {
        this.userService = userService;
        this.temporaryTokenService = temporaryTokenService;
    }

    /**
     * Method that activate user account if the token is valid or redirect when its invalid.
     * @param userId User id to be activated.
     * @param token Token value.
     * @param redirectAttributes Attributes to add messages about activation.
     * @return Redirect to /zaloguj
     */
    @GetMapping("/aktywuj/{userId}")
    public String checkUserToken(@PathVariable Long userId, @RequestParam String token,
                                 RedirectAttributes redirectAttributes) {
        boolean tokenValid = temporaryTokenService.isTokenValid(userId, token);

        if(tokenValid){
            userService.updateUserActivationStatus(userId);
            temporaryTokenService.removeTemporaryToken(token);
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Konto zostało aktywowane");
        }else {
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Link aktywacyjny jest niepoprawny lub już wygasł!");
        }
        return "redirect:/zaloguj";

    }



}

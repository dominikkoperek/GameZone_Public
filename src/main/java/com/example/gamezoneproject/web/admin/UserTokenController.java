package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.user.UserService;
import com.example.gamezoneproject.domain.user.dto.UserResetPasswordDto;
import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy.AccountActivationToken;
import com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy.PasswordResetToken;
import com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy.TemporaryTokenStrategy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final AccountActivationToken activationToken;
    private final PasswordResetToken passwordResetToken;

    public UserTokenController(UserService userService, TemporaryTokenService temporaryTokenService,
                               AccountActivationToken activationToken, PasswordResetToken passwordResetToken) {
        this.userService = userService;
        this.temporaryTokenService = temporaryTokenService;
        this.activationToken = activationToken;
        this.passwordResetToken = passwordResetToken;
    }

    /**
     * Method that activate user account if the token is valid or redirect when its invalid.
     *
     * @param userId             User id to be activated.
     * @param token              Token value.
     * @param redirectAttributes Attributes to add messages about activation.
     * @return Redirect to /zaloguj
     */
    @GetMapping("/aktywuj/{userId}")
    public String activateUserAccount(@PathVariable Long userId, @RequestParam String token,
                                      RedirectAttributes redirectAttributes) {
        boolean tokenValid = isTokenValid(userId, token, activationToken);

        if (tokenValid) {
            userService.updateUserActivationStatus(userId);
            temporaryTokenService.removeTemporaryToken(token);
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Konto zostało aktywowane");
        } else {
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Link aktywacyjny jest niepoprawny lub już wygasł!");
        }
        return "redirect:/zaloguj";

    }

    @GetMapping("/resetuj-haslo/{userId}")
    public String changeUserPasswordForm(Model model,
                                         @PathVariable Long userId,
                                         @RequestParam String token,
                                         RedirectAttributes redirectAttributes) {
        boolean tokenValid = isTokenValid(userId, token, passwordResetToken);
        UserResetPasswordDto userResetPasswordDto = new UserResetPasswordDto();
        if (tokenValid) {
            model.addAttribute("token", token);
            model.addAttribute("user", userResetPasswordDto);
            return "reset-password-form";
        } else {
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Link jest niepoprawny lub już wygasł!");
            return "redirect:/zaloguj";
        }


    }

    @PostMapping("/resetuj-haslo/{userId}")
    public String changeUserPassword(@Valid @ModelAttribute("user") UserResetPasswordDto dto,
                                     BindingResult bindingResult,
                                     @PathVariable Long userId,
                                     @RequestParam String token,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        boolean tokenValid = isTokenValid(userId, token, passwordResetToken);
        if (!bindingResult.hasErrors() && tokenValid) {
            userService.updateUserPassword(userId, dto);
            temporaryTokenService.removeTemporaryToken(token);
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Hasło zostało zmienione");
            return "redirect:/zaloguj";
        } else if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Hasło zawiera błędy!");
            model.addAttribute("token", token);
            return "reset-password-form";
        } else {
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Link jest niepoprawny lub już wygasł!");
        }
        return "redirect:/zaloguj";
    }

    private boolean isTokenValid(Long userId, String token, TemporaryTokenStrategy tokenPurpose) {
        return temporaryTokenService.isTokenValid(userId, token, tokenPurpose);
    }


}

package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.exceptions.AccountLockedException;
import com.example.gamezoneproject.exceptions.TokenIsActiveException;
import com.example.gamezoneproject.domain.user.UserService;
import com.example.gamezoneproject.domain.user.dto.EmailDto;
import com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy.PasswordResetToken;
import com.example.gamezoneproject.mail.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class PasswordResetController {
    private final UserService userService;
    private final PasswordResetToken passwordResetToken;

    public PasswordResetController(UserService userService, PasswordResetToken passwordResetToken) {
        this.userService = userService;
        this.passwordResetToken = passwordResetToken;
    }

    /**
     * Method to bind dto object with form.
     *
     * @param securityContext Current security context.
     * @param model           The Model object that add binding attributes emailDto to form.
     * @return String template send-password-reset-link
     */
    @GetMapping("/resetuj-haslo")
    public String loginForm(@CurrentSecurityContext SecurityContext securityContext, Model model) {
        if (!(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        EmailDto emailDto = new EmailDto();
        model.addAttribute("emailDto", emailDto);
        return "send-password-reset-link";
    }

    /**
     * Method handle with reset password form. Method catch 3 types of exception and send message depends on exception,
     * also method redirect to /zaloguj url.
     *
     * @param email User Email dto object.
     * @param bindingResult Binging object to validate emailDto.
     * @param redirectAttributes Attributes to add flesh Attribute message.
     * @return String url /zaloguj.
     */
    @PostMapping("/resetuj-haslo")
    public String resetPassword(@Valid @ModelAttribute("emailDto") EmailDto email,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "send-password-reset-link";
        } else {
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            try {
                userService.setUserTemporaryToken(email.getEmail(), url, passwordResetToken);
            } catch (TokenIsActiveException e) {
                redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                        "Mail został już wysłany! Sprawdź spam lub poczekaj, nowy mail może zostać wysłany co %s minut"
                                .formatted(EmailServiceImpl.MAIL_SEND_COOLDOWN_MINUTES));
                return "redirect:/zaloguj";
            } catch (UsernameNotFoundException | AccountLockedException e) {
                redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                        "Konto jest zablokowane lub nie istnieje! Spróbuj najpierw je aktywować.");
                return "redirect:/zaloguj";
            }
        }
        redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                "Jeżeli do podanego adresu email (%s) jest przypisane konto to otrzymasz tam dalsze instrukcje."
                        .formatted(email.getEmail()));
        return "redirect:/zaloguj";

    }
}

package com.example.gamezoneproject.web.admin;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    public static final String NOTIFICATION_ATTRIBUTE = "notification";

    /**
     * Method for handling login form.
     * @param securityContext current security context.
     * @return login-form template.
     */
    @GetMapping("/zaloguj")
    public String loginForm(@CurrentSecurityContext SecurityContext securityContext) {
        if (!(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "login-form";
    }

}

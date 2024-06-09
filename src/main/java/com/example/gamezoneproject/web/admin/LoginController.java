package com.example.gamezoneproject.web.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class LoginController {
    public static final String NOTIFICATION_ATTRIBUTE = "notification";

    /**
     * Method for handling login form.
     * @param securityContext current security context.
     * @return login-form template.
     */
    @GetMapping("/zaloguj")
    public String loginForm(@CurrentSecurityContext SecurityContext securityContext,
                            @RequestHeader String referer,
                            HttpSession session) {
        if (!(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        if(referer!=null && !referer.contains("/zaloguj")){
            session.setAttribute("successLoginRedirect",referer);
        }

        return "login-form";
    }
}

package com.example.gamezoneproject.config.secuirty;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class SuccessLoginHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String successLoginRedirect = (String)session.getAttribute("successLoginRedirect");
            if (successLoginRedirect != null) {
                session.removeAttribute("successLoginRedirect");
                response.sendRedirect(successLoginRedirect);
                return;
            }
        }
        response.sendRedirect("/");
    }
}

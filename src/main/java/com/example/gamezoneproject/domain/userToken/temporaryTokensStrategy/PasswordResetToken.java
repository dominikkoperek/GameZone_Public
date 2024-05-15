package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

import org.springframework.stereotype.Service;

@Service
public class PasswordResetToken implements TemporaryTokenStrategy{
    @Override
    public String getDescription() {
        return "Resetowanie hasła";
    }

    @Override
    public String getUrlParam() {
        return "resetuj-haslo";
    }

    @Override
    public int getTokenLifeTimeMinutes() {
        return 30;
    }

    @Override
    public String getEmailTemplate() {
        return "emailsTemplates/password-reset-email.html";
    }

    @Override
    public String getName() {
        return "PASSWORD_RESET";
    }
}

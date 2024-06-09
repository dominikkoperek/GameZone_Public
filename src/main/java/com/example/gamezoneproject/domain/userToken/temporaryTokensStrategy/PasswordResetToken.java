package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetToken implements TemporaryTokenStrategy{
    private final int token_life_time;

    public PasswordResetToken(@Value("${temporary-token.life-time}") int token_life_time) {
        this.token_life_time = token_life_time;
    }
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
        return token_life_time;
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

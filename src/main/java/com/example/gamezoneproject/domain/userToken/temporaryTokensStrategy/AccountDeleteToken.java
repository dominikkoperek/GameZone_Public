package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountDeleteToken implements TemporaryTokenStrategy{
    private final int token_life_time;

    public AccountDeleteToken(@Value("${temporary-token.life-time}") int token_life_time) {
        this.token_life_time = token_life_time;
    }
    @Override
    public String getDescription() {
        return "Usuń konto";
    }

    @Override
    public String getUrlParam() {
        return "usuwanie-konta";
    }

    @Override
    public int getTokenLifeTimeMinutes() {
        return token_life_time;
    }

    @Override
    public String getEmailTemplate() {
        return "";
    }

    @Override
    public String getName() {
        return "ACCOUNT_DELETE";
    }
}

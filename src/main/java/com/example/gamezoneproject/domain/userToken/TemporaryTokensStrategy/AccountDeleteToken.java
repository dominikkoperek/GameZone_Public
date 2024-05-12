package com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountDeleteToken implements TemporaryTokenStrategy{
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
        return 30;
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

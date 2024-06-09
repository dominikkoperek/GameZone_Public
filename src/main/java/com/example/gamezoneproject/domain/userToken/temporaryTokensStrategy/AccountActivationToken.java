package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountActivationToken implements TemporaryTokenStrategy {
    private final int token_life_time;

    public AccountActivationToken(@Value("${temporary-token.life-time}") int token_life_time) {
        this.token_life_time = token_life_time;
    }

    @Override
    public String getDescription() {
        return "Aktywacja konta";
    }

    @Override
    public String getUrlParam() {
        return "aktywuj";
    }

    @Override
    public int getTokenLifeTimeMinutes() {
        return token_life_time;
    }

    @Override
    public String getEmailTemplate() {
        return "emailsTemplates/account-activation-email.html";
    }

    @Override
    public String getName() {
        return "ACCOUNT_ACTIVATION";
    }

}

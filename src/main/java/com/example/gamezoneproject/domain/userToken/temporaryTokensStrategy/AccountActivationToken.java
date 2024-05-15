package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import org.springframework.stereotype.Service;

@Service
public class AccountActivationToken implements TemporaryTokenStrategy {
    private final TemporaryTokenService temporaryTokenService;

    public AccountActivationToken(TemporaryTokenService temporaryTokenService) {
        this.temporaryTokenService = temporaryTokenService;
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
        return 30;
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

package com.example.gamezoneproject.domain.userToken.temporaryTokensStrategy;

public interface TemporaryTokenStrategy {

    String getDescription();
    String getUrlParam();
    int getTokenLifeTimeMinutes();
    String getEmailTemplate();
    String getName();
}

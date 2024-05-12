package com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy;

import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;

import java.time.LocalDateTime;

public interface TemporaryTokenStrategy {

    String getDescription();
    String getUrlParam();
    int getTokenLifeTimeMinutes();
    String getEmailTemplate();
    String getName();
}

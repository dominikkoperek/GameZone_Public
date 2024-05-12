package com.example.gamezoneproject.mail;

import com.example.gamezoneproject.domain.user.User;
import com.example.gamezoneproject.domain.user.dto.UserTokenDto;
import com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy.TemporaryTokenStrategy;

public interface MailService {


    void buildEmail(User user, String url, TemporaryTokenStrategy linkPurpose);

    String buildVerifyLink(UserTokenDto userTokenDto, String url, String linkPurpose);
}

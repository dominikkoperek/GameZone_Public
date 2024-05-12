package com.example.gamezoneproject.domain.userToken;

import com.example.gamezoneproject.domain.userToken.dto.TemporaryTokenDto;

/**
 * Simple dto mapper to map Dto to Entity.
 */
public class TemporaryTokenDtoMapper {
    /**
     * Mapper that change dto to entity
     * @param temporaryToken dto of the temporary token
     * @return new TemporaryToken entity.
     */
    public static TemporaryToken map (TemporaryTokenDto temporaryToken){
        TemporaryToken token = new TemporaryToken();
        token.setToken(temporaryToken.getToken());
        token.setId(temporaryToken.getId());
        token.setTokenExperienceTime(temporaryToken.getTokenExperienceTime());
        token.setTokenName(temporaryToken.getTokenName());
        token.setLastTokenSend(temporaryToken.getLastTokenSend());
        return token;
    }
    /**
     * Mapper that change dto to entity
     * @param temporaryToken dto of the temporary token
     * @return new TemporaryToken entity.
     */
    public static TemporaryTokenDto map (TemporaryToken temporaryToken){
        TemporaryTokenDto token = new TemporaryTokenDto();
        token.setToken(temporaryToken.getToken());
        token.setId(temporaryToken.getId());
        token.setTokenExperienceTime(temporaryToken.getTokenExperienceTime());
        token.setTokenName(temporaryToken.getTokenName());
        token.setLastTokenSend(temporaryToken.getLastTokenSend());
        return token;
    }
}

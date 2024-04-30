package com.example.gamezoneproject.domain.user;

import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;
import com.example.gamezoneproject.domain.user.dto.UserTokenDto;

import java.util.stream.Collectors;

/**
 * Class for mapping entity to dto.
 */
public class UserDtoMapper {
    /**
     * Method to map user entity to UserCredentialsDto for register.
     * @param user User entity
     * @return new User Credentials Dto object with all necessary fields.
     */
    public static UserCredentialsDto map(User user) {
        return new UserCredentialsDto(
                user.getLogin(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(UserRole::getName)
                        .collect(Collectors.toSet()),
                user.isActive()
        );
    }
    /**
     * Method to map user entity to UserTokenDto for generating temporary token.
     * @param user User entity
     * @return new User Credentials Dto object with id login and token fields.
     */
    public static UserTokenDto mapUserTokenDto(User user) {
        return new UserTokenDto(
                user.getId(),
                user.getLogin(),
                user.getToken().getToken()
        );
    }
}

package com.example.gamezoneproject.domain.user;

import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;

import java.util.stream.Collectors;

public class UserDtoMapper {
    static UserCredentialsDto map (User user){
        return new UserCredentialsDto(
                user.getLogin(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(UserRole::getName)
                        .collect(Collectors.toSet())
        );
    }
}

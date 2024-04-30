package com.example.gamezoneproject.domain.user;

import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;
import com.example.gamezoneproject.domain.user.dto.UserRegistrationDto;
import com.example.gamezoneproject.domain.user.dto.UserTokenDto;
import com.example.gamezoneproject.domain.userToken.*;
import com.example.gamezoneproject.domain.userToken.dto.TemporaryTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for new user registration, and setting params to them.
 */
@Service
public class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TemporaryTokenService temporaryTokenService;
    private final int tokenLifeTimeMinutes;

    public UserService(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
                       UserRepository userRepository, TemporaryTokenService temporaryTokenService,
                       @Value("${temporary-token.life-time}")int tokenLifeTimeMinutes) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.temporaryTokenService = temporaryTokenService;
        this.tokenLifeTimeMinutes = tokenLifeTimeMinutes;
    }

    public Optional<UserCredentialsDto> findCredentialsByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login)
                .map(UserDtoMapper::map);
    }

    /**
     * Register new user with default User role.
     * Method build new temporary token.
     *
     * @param userRegistrationDto User registration DTO object to hold all information to be saved.
     */
    @Transactional
    public void registerUserWithDefaultRole(UserRegistrationDto userRegistrationDto) {
        UserRole defaultRole = userRoleRepository
                .findByName(DEFAULT_USER_ROLE)
                .orElseThrow(NoSuchElementException::new);
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail().trim());
        user.setLogin(userRegistrationDto.getLogin().trim());
        user.getRoles()
                .add(defaultRole);
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setToken(buildTemporaryToken());
        userRepository.save(user);
    }

    /**
     * Method to activate user account
     *
     * @param userId The id of the user account to be enabled.
     */
    @Transactional
    public void updateUserActivationStatus(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setActive(true);
            user.setToken(null);
            userRepository.save(user);
        }

    }

    private TemporaryToken buildTemporaryToken() {
        TemporaryTokenDto temporaryTokenDto = new TemporaryTokenDto(
                TemporaryTokenNames.ACCOUNT_ACTIVATION,
                temporaryTokenService.generateTokenValue(),
                LocalDateTime.now().plusMinutes(tokenLifeTimeMinutes)
        );
        return TemporaryTokenDtoMapper.map(temporaryTokenDto);
    }

    /**
     * Check if the login is available to register.
     * @param login New login to be registered.
     * @return True if logins is available or false otherwise.
     */
    public boolean isLoginAvailable(String login) {
        return userRepository
                .findByLoginIgnoreCase(login)
                .isEmpty();
    }

    /**
     * Check if the email is available to register.
     * @param email New email to be registered.
     * @return True if email is available or false otherwise.
     */
    public boolean isEmailAvailable(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .isEmpty();
    }

}

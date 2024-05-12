package com.example.gamezoneproject.domain.user;

import com.example.gamezoneproject.domain.exceptions.AccountLockedException;
import com.example.gamezoneproject.domain.exceptions.TokenIsActiveException;
import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;
import com.example.gamezoneproject.domain.user.dto.UserRegistrationDto;
import com.example.gamezoneproject.domain.user.dto.UserResetPasswordDto;
import com.example.gamezoneproject.domain.userToken.*;
import com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy.AccountActivationToken;
import com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy.TemporaryTokenStrategy;
import com.example.gamezoneproject.domain.userToken.dto.TemporaryTokenDto;
import com.example.gamezoneproject.mail.EmailServiceImpl;
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
    private final EmailServiceImpl emailService;
    private final AccountActivationToken activationToken;

    public UserService(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
                       UserRepository userRepository, TemporaryTokenService temporaryTokenService,
                       EmailServiceImpl emailService, AccountActivationToken activationToken) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.temporaryTokenService = temporaryTokenService;
        this.emailService = emailService;
        this.activationToken = activationToken;
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
    public void registerUserWithDefaultRole(UserRegistrationDto userRegistrationDto, String url) {
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

        TemporaryTokenDto temporaryTokenDto = temporaryTokenService
                .buildTemporaryToken(activationToken);
        user.setToken(TemporaryTokenDtoMapper.map(temporaryTokenDto));
        userRepository.save(user);
        emailService.buildEmail(user, url, activationToken);
    }


    /**
     * Method to activate user account
     *
     * @param userId The id of the user account to be enabled.
     */
    @Transactional
    public void updateUserActivationStatus(Long userId) {
        userRepository.findById(userId)
                .map(user -> {
                    user.setActive(true);
                    user.setToken(null);
                    return user;
                })
                .ifPresent(userRepository::save);
    }


    /**
     * Check if the login is available to register.
     *
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
     *
     * @param email New email to be registered.
     * @return True if email is available or false otherwise.
     */
    public boolean isEmailAvailable(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .isEmpty();
    }

    /**
     * Method generate temporary token and bind it to user by email. Method can throw 3 diffrent types of exception
     * if user is not found, user account is disabled, token is active.
     *
     * @param userEmail    User email to set new temporary token.
     * @param url          Url to build token redirect address.
     * @param tokenPurpose Token purpose.
     */
    @Transactional
    public void setUserTemporaryToken(String userEmail, String url, TemporaryTokenStrategy tokenPurpose) {
        User user = findActiveUserByEmail(userEmail);
        TemporaryTokenDto temporaryToken = temporaryTokenService.buildTemporaryToken(tokenPurpose);

        if (user.getToken() != null) {
            handleExistingToken(user, temporaryToken);
        } else {
            user.setToken(TemporaryTokenDtoMapper.map(temporaryToken));
        }

        emailService.buildEmail(user, url, tokenPurpose);
    }

    /**
     * Method for handling existing token, if email cool down is ready its remove old token and generate new one otherwise
     * throw new TokenIsActiveException;
     *
     * @param user           user to check whether token cooldown is ready.
     * @param temporaryToken temporary token to be saved.
     */
    private void handleExistingToken(User user, TemporaryTokenDto temporaryToken) {
        if (isEmailCooldownAvailable(user)) {
            temporaryTokenService.removeTemporaryToken(user.getToken().getToken());
            user.setToken(TemporaryTokenDtoMapper.map(temporaryToken));
        } else {
            throw new TokenIsActiveException();
        }
    }

    /**
     * Method find user by email and check if user is active. If user exists and account is active return this user entity
     * otherwise throw UsernameNotFoundException or AccountLockedException.
     *
     * @param userEmail user email to find account.
     * @return user entity or throw exception.
     */
    private User findActiveUserByEmail(String userEmail) {
        User user = userRepository
                .findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));
        if (!user.isActive()) {
            throw new AccountLockedException();
        }
        return user;
    }

    /**
     * Check if has it been at least EmailServiceImpl.MAIL_SEND_COOLDOWN_MINUTES after now.
     *
     * @param user user to last token check.
     * @return true if token was sent at least EmailServiceImpl.MAIL_SEND_COOLDOWN_MINUTES after now otherwise return false.
     */
    private static boolean isEmailCooldownAvailable(User user) {
        return LocalDateTime.now()
                .isAfter(user.getToken().getLastTokenSend().plusMinutes(EmailServiceImpl.MAIL_SEND_COOLDOWN_MINUTES));
    }

    /**
     * Method for update user password in database.
     * @param userId user id to password be changed.
     * @param dto dto object for new password validation.
     */
    @Transactional
    public void updateUserPassword(Long userId, UserResetPasswordDto dto) {
        if (dto.getPassword().equals(dto.getConfirmPassword())) {
            userRepository
                    .findById(userId)
                    .filter(usr -> usr.getToken() != null)
                    .map(usr -> {
                        usr.setPassword(passwordEncoder.encode(dto.getConfirmPassword()));
                        usr.setToken(null);
                        return usr;
                    })
                    .ifPresent(userRepository::save);
        }
    }

}

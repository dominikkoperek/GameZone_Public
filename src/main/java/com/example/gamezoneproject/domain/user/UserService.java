package com.example.gamezoneproject.domain.user;

import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;
import com.example.gamezoneproject.domain.user.dto.UserRegistrationDto;
import com.example.gamezoneproject.domain.validation.playersrange.PlayersRange;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<UserCredentialsDto> findCredentialsByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login)
                .map(UserDtoMapper::map);
    }
    @Transactional
    public void registerUserWithDefaultRole(UserRegistrationDto userRegistrationDto){
        UserRole defaultRole = userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow(NoSuchElementException::new);
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail().trim());
        user.setLogin(userRegistrationDto.getLogin().trim());
        user.getRoles().add(defaultRole);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(user);
    }
    public boolean isLoginAvailable(String login){
        return userRepository
                .findByLoginIgnoreCase(login)
                .isEmpty();
    }

    public boolean isEmailAvailable(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .isEmpty();
    }
}

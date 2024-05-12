package com.example.gamezoneproject.domain.userToken;

import com.example.gamezoneproject.domain.user.UserDtoMapper;
import com.example.gamezoneproject.domain.user.UserRepository;
import com.example.gamezoneproject.domain.user.dto.UserTokenDto;
import com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy.TemporaryTokenStrategy;
import com.example.gamezoneproject.domain.userToken.dto.TemporaryTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for Temporary tokens, generate random tokens/validate them/add new ones
 */
@Service
public class TemporaryTokenService {

    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'A', 'b', 'B', 'c',
            'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M',
            'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x',
            'X', 'y', 'Y', 'z', 'Z'};
    private final int token_length;
    private final UserRepository userRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final SecureRandom random;
    private final PasswordEncoder passwordEncoder;

    public TemporaryTokenService(TemporaryTokenRepository temporaryTokenRepository,
                                 @Value("${temporary-token.length}") int token_length,
                                 UserRepository userRepository, SecureRandom random,
                                 PasswordEncoder passwordEncoder) {
        this.temporaryTokenRepository = temporaryTokenRepository;
        this.token_length = token_length;
        this.userRepository = userRepository;
        this.random = random;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Generating random token of a specified length.
     *
     * @return token of a specified length.
     */
    public String generateTokenValue() {
        return generateRandomToken(this.token_length);
    }

    private String generateRandomToken(int tokenLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokenLength; i++) {
            sb.append(CHARS[random.nextInt(0, CHARS.length)]);
        }
        return sb.toString();
    }

    /**
     * Method for saving new tokens in db
     *
     * @param temporaryTokenDto Dto object which hold information about token to save.
     */
    @Transactional
    public void addToken(TemporaryTokenDto temporaryTokenDto) {
        TemporaryTokenDto tokenToSave = new TemporaryTokenDto();
        tokenToSave.setTokenName(temporaryTokenDto.getTokenName());
        tokenToSave.setTokenExperienceTime(temporaryTokenDto.getTokenExperienceTime());
        tokenToSave.setToken(generateRandomToken(token_length));
        temporaryTokenRepository.save(TemporaryTokenDtoMapper.map(tokenToSave));
    }

    /**
     * Validate whether token is valid.
     *
     * @param userId User id.
     * @param token  Token value.
     * @return true if token is valid and false if not.
     */
    public boolean isTokenValid(Long userId, String token, TemporaryTokenStrategy tokenName) {
        Optional<UserTokenDto> userTokenDto = userRepository
                .findById(userId)
                .filter(user -> user.getToken() != null)
                .filter(user -> user.getToken().getTokenName().equals(tokenName.getName()))
                .map(UserDtoMapper::mapUserTokenDto);

        Boolean isTokenActive = isTokenActive(token);
        return userTokenDto
                .map(tokenDto -> tokenDto.getToken().equals(token) && isTokenActive)
                .orElse(false);
    }

    /**
     * Validate whether token is active or not.
     * @param token token value to be checked.
     * @return true if token is active or false otherwise.
     */
    private Boolean isTokenActive(String token) {
        return temporaryTokenRepository
                .findByToken(token)
                .map(temporaryToken -> temporaryToken.getTokenExperienceTime().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    /**
     * Method to remove used token.
     *
     * @param token Token value.
     */
    @Transactional
    public void removeTemporaryToken(String token) {
        temporaryTokenRepository.findByToken(token).ifPresent(System.out::println);
        temporaryTokenRepository
                .findByToken(token)
                .ifPresent(temporaryTokenRepository::delete);
    }

    /**
     * Method builds new temporary token dto object.
     * @param tokenPurpose token purpose for new token.
     * @return new TemporaryTokenDto object.
     */
    public TemporaryTokenDto buildTemporaryToken(TemporaryTokenStrategy tokenPurpose) {
        return new TemporaryTokenDto(
                tokenPurpose.getName(),
                generateTokenValue(),
                LocalDateTime.now().plusMinutes(tokenPurpose.getTokenLifeTimeMinutes()).withNano(0),
                LocalDateTime.now().withNano(0)

        );
    }

    /**
     * Finds token by token value.
     * @param token value of token.
     * @return optional of TemporaryTokenDto if token is present or empty optional otherwise.
     */
    public Optional<TemporaryTokenDto> findByName(String token) {
        return temporaryTokenRepository
                .findByToken(token)
                .map(TemporaryTokenDtoMapper::map);
    }

}
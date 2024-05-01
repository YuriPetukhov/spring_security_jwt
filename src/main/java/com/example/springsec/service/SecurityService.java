package com.example.springsec.service;

import com.example.springsec.model.RefreshToken;
import com.example.springsec.model.User;
import com.example.springsec.model.UserRole;
import com.example.springsec.model.dto.TokenData;
import com.example.springsec.model.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service class for managing security-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Processes a password token request.
     *
     * @param username the username
     * @param password the password
     * @return the token data
     */
    public TokenData processPasswordToken(String username, String password) {
        User user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return createTokenData(user);
        } else {
            throw new AuthException("Exception trying to check password for user: " + username);
        }
    }

    /**
     * Processes a refresh token request.
     *
     * @param refreshTokenValue the refresh token value
     * @return the token data
     */
    public TokenData processRefreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.getByValue(refreshTokenValue);
        User user = userService.findById(refreshToken.getUserId());
        return createTokenData(user);
    }

    private TokenData createTokenData(User user) {
        String token = tokenService.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRoles().stream().map(UserRole::getRole).collect(Collectors.toList()));

        RefreshToken refreshToken = refreshTokenService.save(user.getId());
        return new TokenData(token, refreshToken.getValue());
    }
}

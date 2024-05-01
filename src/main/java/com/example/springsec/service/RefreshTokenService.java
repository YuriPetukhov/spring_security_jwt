package com.example.springsec.service;

import com.example.springsec.model.RefreshToken;
import com.example.springsec.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * Service class for managing refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    /**
     * Saves a refresh token for the given user.
     *
     * @param userId the ID of the user
     * @return the saved refresh token
     */
    public RefreshToken save(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setValue(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Retrieves a refresh token by its value.
     *
     * @param refreshToken the value of the refresh token
     * @return the refresh token
     */
    public RefreshToken getByValue(String refreshToken) {
        return refreshTokenRepository.findByValue(refreshToken).orElse(null);
    }
}

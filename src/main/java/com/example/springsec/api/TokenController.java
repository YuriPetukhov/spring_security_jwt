package com.example.springsec.api;

import com.example.springsec.model.dto.PasswordTokenRequest;
import com.example.springsec.model.dto.RefreshTokenRequest;
import com.example.springsec.model.dto.TokenData;
import com.example.springsec.model.dto.TokenResponse;
import com.example.springsec.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for token-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/public/token")
@RequiredArgsConstructor
public class TokenController {

    private final SecurityService securityService;

    /**
     * Endpoint to generate access and refresh tokens using username and password.
     *
     * @param passwordTokenRequest the password token request containing username and password
     * @return a response containing access and refresh tokens
     */
    @PostMapping("/password")
    public ResponseEntity<TokenResponse> password(@RequestBody PasswordTokenRequest passwordTokenRequest) {
        TokenData tokenData = securityService.processPasswordToken(passwordTokenRequest.getUsername(), passwordTokenRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(tokenData.getToken(), tokenData.getRefreshToken()));
    }

    /**
     * Endpoint to refresh access token using refresh token.
     *
     * @param request the refresh token request containing refresh token
     * @return a response containing new access and refresh tokens
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        TokenData tokenData = securityService.processRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new TokenResponse(tokenData.getToken(), tokenData.getRefreshToken()));
    }

}

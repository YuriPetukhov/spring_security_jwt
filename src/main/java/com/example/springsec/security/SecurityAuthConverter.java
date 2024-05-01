package com.example.springsec.security;

import com.example.springsec.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@RequiredArgsConstructor
public class SecurityAuthConverter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    /**
     * Converts a ServerWebExchange to an Authentication.
     *
     * @param exchange the ServerWebExchange
     * @return the Authentication extracted from the exchange
     */
    public Authentication convert(ServerWebExchange exchange) {
        String token = extractBearerToken(exchange);
        return token != null ? tokenService.getAuthentication(token) : null;
    }

    /**
     * Extracts the JWT token from the request's Authorization header.
     *
     * @param exchange the ServerWebExchange
     * @return the extracted JWT token
     */
    private String extractBearerToken(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

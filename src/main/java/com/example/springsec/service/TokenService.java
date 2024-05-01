package com.example.springsec.service;

import com.example.springsec.model.AppUserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Service class for managing JWT tokens.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private static final String ROLE_CLAIM = "role";
    private static final String ID_CLAIM = "id";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Duration tokenExpiration;

    /**
     * Generates a JWT token.
     *
     * @param username the username
     * @param id the user id
     * @param roles the user roles
     * @return the JWT token
     */
    public String generateToken(String username, String id, List<String> roles) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + tokenExpiration.toMillis())))
                .claim(ROLE_CLAIM, roles)
                .claim(ID_CLAIM, id)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Validates a JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Error validating JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets authentication information from a JWT token.
     *
     * @param token the JWT token
     * @return the authentication information
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims tokenBody = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String subject = tokenBody.getSubject();
        String id = tokenBody.get(ID_CLAIM, String.class);
        List<String> roles = (List<String>) tokenBody.get(ROLE_CLAIM);

        Principal principal = new AppUserPrincipal(subject, id, roles);

        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
    }
}

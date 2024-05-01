package com.example.springsec.security;

import com.example.springsec.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Custom authentication manager to authenticate users.
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    /**
     * Performs authentication for the user.
     *
     * @param authentication the authentication request
     * @return the authenticated user
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.findByUsername(username);

        if (userDetails != null && userDetails.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            return null;
        }
    }

    /**
     * Indicates whether this AuthenticationProvider supports the specified authentication object.
     *
     * @param authentication the authentication class to be tested
     * @return true if the authentication class is supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

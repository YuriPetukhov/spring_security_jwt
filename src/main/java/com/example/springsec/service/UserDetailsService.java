package com.example.springsec.service;

import com.example.springsec.model.AppUserDetails;
import com.example.springsec.model.User;
import com.example.springsec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class to load user-specific data.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return the UserDetails object
     */
    public UserDetails findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return new AppUserDetails(user);
    }

}

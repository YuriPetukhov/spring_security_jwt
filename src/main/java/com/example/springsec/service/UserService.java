package com.example.springsec.service;

import com.example.springsec.model.User;
import com.example.springsec.model.exception.UserNotFoundException;
import com.example.springsec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user.
     *
     * @param user the user to be created
     * @return the created user
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return the user
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user
     * @return the user
     * @throws UserNotFoundException if the user is not found
     */
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}

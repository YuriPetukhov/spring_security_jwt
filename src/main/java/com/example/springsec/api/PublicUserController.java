package com.example.springsec.api;

import com.example.springsec.model.RoleType;
import com.example.springsec.model.User;
import com.example.springsec.model.UserRole;
import com.example.springsec.model.dto.CreateUserRequest;
import com.example.springsec.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for public user-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserService userService;

    /**
     * Endpoint to create a new user.
     *
     * @param request the user creation request containing user details
     * @return a response indicating whether the user creation was successful
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        // Convert role strings to UserRole objects
        Set<UserRole> roles = request.getRoles().stream()
                .map(role -> UserRole.builder().role(RoleType.valueOf(role.toUpperCase()).name()).build())
                .collect(Collectors.toSet());

        // Create a new user object
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .roles(roles)
                .build();

        // Save the user using UserService
        userService.createUser(user);
        return ResponseEntity.ok("User created");
    }

}

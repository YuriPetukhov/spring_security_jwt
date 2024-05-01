package com.example.springsec.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related endpoints.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Endpoint accessible to users with 'USER' authority.
     *
     * @return a string indicating the user role
     */
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String user() {
        return "User";
    }
}

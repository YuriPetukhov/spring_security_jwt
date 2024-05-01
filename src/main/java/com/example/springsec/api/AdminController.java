package com.example.springsec.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for admin-related endpoints.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * Endpoint to access admin resources.
     * Only users with the 'ADMIN' authority can access this endpoint.
     *
     * @return a message indicating successful access to admin resources
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Admin";
    }

}

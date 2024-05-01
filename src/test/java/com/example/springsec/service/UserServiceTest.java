package com.example.springsec.service;

import com.example.springsec.TestSpringSecApplication;
import com.example.springsec.model.RoleType;
import com.example.springsec.model.User;
import com.example.springsec.model.UserRole;
import com.example.springsec.model.exception.UserNotFoundException;
import com.example.springsec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = TestSpringSecApplication.class)
@RequiredArgsConstructor
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setPassword("testpassword");

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(1L, "USER"));

        user.setRoles(roles);

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());

        assertEquals(user.getRoles(), savedUser.getRoles());
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setPassword("testpassword");

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(1L, "USER"));

        user.setRoles(roles);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());

        assertEquals(user.getRoles(), foundUser.getRoles());

    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setPassword("testpassword");

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(1L, "USER"));

        user.setRoles(roles);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = userService.findById("1");

        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());

        assertEquals(user.getRoles(), foundUser.getRoles());

    }

    @Test
    public void testFindById_UserNotFoundException() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById("1");
        });
    }
}

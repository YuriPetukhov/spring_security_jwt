package com.example.springsec.service;

import com.example.springsec.TestSpringSecApplication;
import com.example.springsec.model.RefreshToken;
import com.example.springsec.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = TestSpringSecApplication.class)
@RequiredArgsConstructor
class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestSpringSecApplication.postgresContainer();

    @BeforeEach
    void setUp() {
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveRefreshToken() {
        String userId = "user_id";
        RefreshToken refreshToken = refreshTokenService.save(userId);
        assertEquals(userId, refreshToken.getUserId());
    }
}

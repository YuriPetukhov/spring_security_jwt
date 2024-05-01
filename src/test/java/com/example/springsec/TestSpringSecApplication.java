package com.example.springsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringSecApplication {

	@Bean
	@ServiceConnection
	public static PostgreSQLContainer<?> postgresContainer() {
		PostgreSQLContainer<?> container =
				new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
						.withDatabaseName("token_security")
						.withUsername("YuriPetukhov")
						.withPassword("YuriPetukhov");
		container.start();
		return container;
	}

	public static void main(String[] args) {
		SpringApplication.from(SpringSecApplication::main).with(TestSpringSecApplication.class).run(args);
	}
}

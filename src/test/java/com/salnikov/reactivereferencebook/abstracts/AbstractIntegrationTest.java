package com.salnikov.reactivereferencebook.abstracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

/**
 * Абстрактный класс для интеграционных тестов.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Testcontainers
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_CLASS)
public abstract class AbstractIntegrationTest extends AbstractTest {

    @Autowired
    protected WebTestClient webClient;

    @Container
    @ServiceConnection
    protected static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15");

}

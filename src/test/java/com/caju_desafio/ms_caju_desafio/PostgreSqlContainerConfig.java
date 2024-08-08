package com.caju_desafio.ms_caju_desafio;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.concurrent.atomic.AtomicBoolean;

public class PostgreSqlContainerConfig implements BeforeAllCallback {
    private static AtomicBoolean containerStarted = new AtomicBoolean(false);
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("caju-desafio")
            .withUsername("caju")
            .withPassword("caju");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if(!containerStarted.get()) {
            postgreSQLContainer.start();

            System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
            System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
            System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());

        }
    }
}
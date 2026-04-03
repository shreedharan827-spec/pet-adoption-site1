package com.petadoption.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties properties, Environment env) {
        String databaseUrl = env.getProperty("DATABASE_URL");

        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            properties.setUrl("jdbc:" + databaseUrl);
            properties.setDriverClassName("org.postgresql.Driver");
        }

        // In Render, DATABASE_URL can be a Postgres URI. Convert to JDBC prefix if needed.
        // 'DB_USERNAME' and 'DB_PASSWORD' are provided by the Render DB service, but spring.datasource.* takes precedence
        String dbUser = env.getProperty("DB_USERNAME");
        String dbPass = env.getProperty("DB_PASSWORD");

        if (dbUser != null) {
            properties.setUsername(dbUser);
        }
        if (dbPass != null) {
            properties.setPassword(dbPass);
        }

        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
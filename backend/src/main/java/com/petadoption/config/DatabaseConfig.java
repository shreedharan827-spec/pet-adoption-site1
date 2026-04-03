package com.petadoption.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties properties, Environment env) {
        String databaseUrl = env.getProperty("DATABASE_URL");

        if (databaseUrl != null && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            URI uri = URI.create(databaseUrl.replaceFirst("^postgres", "postgresql"));

            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();
            String path = uri.getPath() == null ? "" : uri.getPath();
            String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", host, port, path);

            properties.setUrl(jdbcUrl);
            properties.setDriverClassName("org.postgresql.Driver");

            if (properties.getUsername() == null || properties.getUsername().isEmpty()) {
                String user = null;
                String pass = null;

                if (uri.getUserInfo() != null) {
                    String[] userInfo = uri.getUserInfo().split(":", 2);
                    user = userInfo[0];
                    if (userInfo.length > 1) {
                        pass = userInfo[1];
                    }
                }

                String envUser = env.getProperty("DB_USERNAME");
                String envPass = env.getProperty("DB_PASSWORD");

                properties.setUsername(envUser != null ? envUser : user);
                properties.setPassword(envPass != null ? envPass : pass);
            }
        }

        if (properties.getUrl() == null || properties.getUrl().isBlank()) {
            properties.setUrl("jdbc:postgresql://localhost:5432/pet_adoption_site");
            properties.setDriverClassName("org.postgresql.Driver");
        }

        if (properties.getUsername() == null) {
            properties.setUsername("petuser");
        }
        if (properties.getPassword() == null) {
            properties.setPassword("");
        }

        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
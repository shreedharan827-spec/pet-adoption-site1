package com.petadoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Application Class for PawsAdopt Backend API
 * Entry point for the Spring Boot application
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.petadoption")
public class PawsAdoptApplication {

    public static void main(String[] args) {
        SpringApplication.run(PawsAdoptApplication.class, args);
        System.out.println("🐾 PawsAdopt Backend API Started Successfully!");
        System.out.println("📚 API Documentation: http://localhost:8080/api/swagger-ui.html");
        System.out.println("🗄️ H2 Console: http://localhost:8080/api/h2-console");
    }
}

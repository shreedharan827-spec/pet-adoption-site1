package com.petadoption.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * User Entity - Represents a registered user in the system
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String aadharNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String securityQuestion1;

    @Column(nullable = false)
    private String securityAnswer1;

    @Column(nullable = false)
    private String securityQuestion2;

    @Column(nullable = false)
    private String securityAnswer2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isVerified = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    public enum Role {
        USER, ADMIN, SHELTER
    }

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

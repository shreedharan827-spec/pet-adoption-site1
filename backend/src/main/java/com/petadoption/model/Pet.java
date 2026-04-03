package com.petadoption.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Pet Entity - Represents a pet available for adoption
 */
@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AdoptionStatus status = AdoptionStatus.AVAILABLE;

    @Column(nullable = true)
    private String size;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isVaccinated = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isNeutered = false;

    @Column(length = 1000)
    private String healthNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

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

    /**
     * Pet Type Enum
     */
    public enum PetType {
        DOG, CAT, RABBIT, BIRD, HAMSTER, GUINEA_PIG, OTHER
    }

    /**
     * Gender Enum
     */
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    /**
     * Adoption Status Enum
     */
    public enum AdoptionStatus {
        AVAILABLE,
        APPROVED,
        ADOPTED
    }
}
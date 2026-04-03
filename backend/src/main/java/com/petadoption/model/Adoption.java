package com.petadoption.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Adoption Entity - Represents an adoption request/application
 */
@Entity
@Table(name = "adoptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AdoptionStatus status = AdoptionStatus.PENDING;

    @Column(length = 500)
    private String adoptionReason;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime requestedAt = LocalDateTime.now();

    @Column
    private LocalDateTime completedAt;

    @Column
    private LocalDateTime rejectedAt;

    @Column(length = 500)
    private String rejectionReason;

    @PreUpdate
    protected void onUpdate() {
        // Automatically set completedAt when adoption is approved
        if (status == AdoptionStatus.APPROVED && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    /**
     * Adoption Status Enum
     */
    public enum AdoptionStatus {
        PENDING, APPROVED, REJECTED, CANCELLED
    }
}

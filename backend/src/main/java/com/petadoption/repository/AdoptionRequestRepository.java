package com.petadoption.repository;

import com.petadoption.model.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for AdoptionRequest Entity
 * Handles database operations for adoption requests
 */
@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    List<AdoptionRequest> findByUserId(Long userId);
    List<AdoptionRequest> findByPetId(Long petId);
    List<AdoptionRequest> findByStatus(AdoptionRequest.Status status);
    List<AdoptionRequest> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<AdoptionRequest> findByUserIdAndPetIdAndStatus(Long userId, Long petId, AdoptionRequest.Status status);
}

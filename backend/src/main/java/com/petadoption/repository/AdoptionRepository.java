package com.petadoption.repository;

import com.petadoption.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Adoption Entity
 * Handles database operations for Adoption
 */
@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserId(Long userId);
    List<Adoption> findByPetId(Long petId);
    List<Adoption> findByStatus(Adoption.AdoptionStatus status);
    Optional<Adoption> findByUserIdAndPetId(Long userId, Long petId);
}

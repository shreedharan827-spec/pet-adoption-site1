package com.petadoption.repository;

import com.petadoption.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Pet Entity
 * Handles database operations for Pet
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByType(Pet.PetType type);
    List<Pet> findByStatus(Pet.AdoptionStatus status);
    List<Pet> findByTypeAndStatus(Pet.PetType type, Pet.AdoptionStatus status);
    
    @Query("SELECT p FROM Pet p WHERE p.status = 'AVAILABLE' ORDER BY p.createdAt DESC")
    List<Pet> findAllAvailable();
    
    List<Pet> findByBreedContainingIgnoreCase(String breed);
    
    long countByStatus(Pet.AdoptionStatus status);
    
    List<Pet> findByStatusOrderByCreatedAtDesc(Pet.AdoptionStatus status);
}

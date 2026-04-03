package com.petadoption.service;

import com.petadoption.model.Pet;
import com.petadoption.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for Pet Management
 * Handles business logic for pet operations
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    /**
     * Get all available pets for adoption
     */
    public List<Pet> getAllAvailablePets() {
        System.out.println("[INFO] Fetching all available pets");
        return petRepository.findByStatus(Pet.AdoptionStatus.AVAILABLE);
    }

    /**
     * Get pets by type
     */
    public List<Pet> getPetsByType(Pet.PetType type) {
        System.out.println("[INFO] Fetching pets of type: " + type);

        return petRepository.findByTypeAndStatus(
                type,
                Pet.AdoptionStatus.AVAILABLE
        );
    }

    /**
     * Get pet by ID
     */
    public Pet getPetById(Long petId) {
        System.out.println("[INFO] Fetching pet with ID: " + petId);

        return petRepository.findById(petId)
                .orElseThrow(() ->
                        new RuntimeException("Pet not found with ID: " + petId));
    }

    /**
     * Create new pet
     */
    public Pet createPet(Pet pet) {
        System.out.println("[INFO] Creating new pet: " + pet.getName());

        if (pet.getStatus() == null) {
            // default to available
            pet.setStatus(Pet.AdoptionStatus.AVAILABLE);
        }

        return petRepository.save(pet);
    }

    /**
     * Update pet details
     */
    public Pet updatePet(Long petId, Pet petDetails) {
        Pet pet = getPetById(petId);

        pet.setName(petDetails.getName());
        pet.setBreed(petDetails.getBreed());
        pet.setDescription(petDetails.getDescription());
        pet.setAge(petDetails.getAge());
        pet.setGender(petDetails.getGender());
        pet.setStatus(petDetails.getStatus());
        pet.setIsVaccinated(petDetails.getIsVaccinated());
        pet.setIsNeutered(petDetails.getIsNeutered());
        pet.setHealthNotes(petDetails.getHealthNotes());

        System.out.println("[INFO] Updated pet with ID: " + petId);

        return petRepository.save(pet);
    }

    /**
     * Delete pet by ID
     */
    public void deletePet(Long petId) {
        System.out.println("[INFO] Deleting pet with ID: " + petId);

        if (!petRepository.existsById(petId)) {
            throw new RuntimeException("Pet not found with ID: " + petId);
        }

        petRepository.deleteById(petId);
    }

    /**
     * Get count of available pets
     */
    public long getAvailablePetsCount() {
        return petRepository.countByStatus(Pet.AdoptionStatus.AVAILABLE);
    }

    /**
     * Get all adopted pets
     */
    public List<Pet> getAdoptedPets() {
        System.out.println("[INFO] Fetching all adopted pets");

        return petRepository.findByStatusOrderByCreatedAtDesc(
                Pet.AdoptionStatus.ADOPTED
        );
    }

    /**
     * Get pending pets (available for approval)
     */
    public List<Pet> getPendingPets() {
        System.out.println("[INFO] Fetching pending pets");

        return petRepository.findByStatus(Pet.AdoptionStatus.AVAILABLE);
    }

    /**
     * Approve pet for adoption
     */
    public Pet approvePet(Long petId) {
        Pet pet = getPetById(petId);

        if (pet.getStatus() != Pet.AdoptionStatus.AVAILABLE) {
            throw new RuntimeException("Only available pets can be approved");
        }

        pet.setStatus(Pet.AdoptionStatus.APPROVED);

        System.out.println("[INFO] Approved pet with ID: " + petId);

        return petRepository.save(pet);
    }

    /**
     * Reject / delete pet
     */
    public void rejectPet(Long petId) {
        getPetById(petId); // validate exists

        System.out.println("[INFO] Rejected pet with ID: " + petId);

        petRepository.deleteById(petId);
    }
}
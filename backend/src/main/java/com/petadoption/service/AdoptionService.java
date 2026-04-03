package com.petadoption.service;

import com.petadoption.model.Adoption;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.repository.AdoptionRepository;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for Adoption Management
 * Handles business logic for adoption requests
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    /**
     * Create adoption request
     */
    public Adoption createAdoptionRequest(Long userId, Long petId, String adoptionReason) {
        System.out.println("[INFO] Creating adoption request - User: " + userId + ", Pet: " + petId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Check if pet is available
        if (pet.getStatus() != Pet.AdoptionStatus.AVAILABLE) {
            throw new RuntimeException("Pet is not available for adoption");
        }

        // Check if user already has pending request for this pet
        adoptionRepository.findByUserIdAndPetId(userId, petId)
                .filter(a -> a.getStatus() == Adoption.AdoptionStatus.PENDING)
                .ifPresent(a -> {
                    throw new RuntimeException("Adoption request already exists for this pet");
                });

        Adoption adoption = Adoption.builder()
                .user(user)
                .pet(pet)
                .adoptionReason(adoptionReason)
                .status(Adoption.AdoptionStatus.PENDING)
                .build();

        Adoption savedAdoption = adoptionRepository.save(adoption);

        System.out.println("[INFO] Adoption request created - ID: " + savedAdoption.getId());

        return savedAdoption;
    }

    /**
     * Get adoption requests by user
     */
    public List<Adoption> getUserAdoptionRequests(Long userId) {
        return adoptionRepository.findByUserId(userId);
    }

    /**
     * Get adoption requests by pet
     */
    public List<Adoption> getPetAdoptionRequests(Long petId) {
        return adoptionRepository.findByPetId(petId);
    }

    /**
     * Get adoption request by ID
     */
    public Adoption getAdoptionById(Long adoptionId) {
        return adoptionRepository.findById(adoptionId)
                .orElseThrow(() -> new RuntimeException("Adoption request not found"));
    }

    /**
     * Approve adoption request
     */
    public Adoption approveAdoption(Long adoptionId, String notes) {
        Adoption adoption = getAdoptionById(adoptionId);

        adoption.setStatus(Adoption.AdoptionStatus.APPROVED);
        adoption.setNotes(notes);

        // Update pet status
        Pet pet = adoption.getPet();
        pet.setStatus(Pet.AdoptionStatus.ADOPTED);
        petRepository.save(pet);

        return adoptionRepository.save(adoption);
    }

    /**
     * Reject adoption request
     */
    public Adoption rejectAdoption(Long adoptionId, String rejectionReason) {
        Adoption adoption = getAdoptionById(adoptionId);

        adoption.setStatus(Adoption.AdoptionStatus.REJECTED);
        adoption.setRejectionReason(rejectionReason);

        return adoptionRepository.save(adoption);
    }

    /**
     * Cancel adoption request
     */
    public Adoption cancelAdoption(Long adoptionId) {
        Adoption adoption = getAdoptionById(adoptionId);

        adoption.setStatus(Adoption.AdoptionStatus.CANCELLED);

        return adoptionRepository.save(adoption);
    }

    /**
     * Get all pending adoption requests
     */
    public List<Adoption> getAllPendingRequests() {
        return adoptionRepository.findByStatus(Adoption.AdoptionStatus.PENDING);
    }

    /**
     * Get adoption statistics
     */
    public AdoptionStats getAdoptionStats() {
        long totalRequests = adoptionRepository.count();
        long pendingRequests = adoptionRepository.findByStatus(Adoption.AdoptionStatus.PENDING).size();
        long approvedRequests = adoptionRepository.findByStatus(Adoption.AdoptionStatus.APPROVED).size();
        long rejectedRequests = adoptionRepository.findByStatus(Adoption.AdoptionStatus.REJECTED).size();

        return AdoptionStats.builder()
                .totalRequests(totalRequests)
                .pendingRequests(pendingRequests)
                .approvedRequests(approvedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    /**
     * Adoption Statistics DTO
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class AdoptionStats {
        private long totalRequests;
        private long pendingRequests;
        private long approvedRequests;
        private long rejectedRequests;
    }
}
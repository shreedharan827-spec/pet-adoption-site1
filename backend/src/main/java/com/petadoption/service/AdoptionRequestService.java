package com.petadoption.service;

import com.petadoption.model.AdoptionRequest;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.repository.AdoptionRequestRepository;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for Adoption Request Management
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    /**
     * Submit a new adoption request
     */
    public AdoptionRequest submitAdoptionRequest(Long userId, Long petId, String message) {

        System.out.println("Submitting adoption request - User: " + userId + ", Pet: " + petId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Check pet availability
        if (pet.getStatus() != Pet.AdoptionStatus.AVAILABLE) {
            throw new RuntimeException(
                    "Pet is not available for adoption. Status: " + pet.getStatus()
            );
        }

        // Check for existing pending request
        Optional<AdoptionRequest> existingRequest =
                adoptionRequestRepository.findByUserIdAndPetIdAndStatus(
                        userId,
                        petId,
                        AdoptionRequest.Status.PENDING
                );

        if (existingRequest.isPresent()) {
            throw new RuntimeException(
                    "You already have a pending adoption request for this pet"
            );
        }

        AdoptionRequest request = AdoptionRequest.builder()
                .user(user)
                .pet(pet)
                .message(message)
                .status(AdoptionRequest.Status.PENDING)
                .build();

        AdoptionRequest savedRequest = adoptionRequestRepository.save(request);

        System.out.println("Adoption request submitted - ID: " + savedRequest.getId());

        return savedRequest;
    }

    /**
     * Get adoption requests for user
     */
    public List<AdoptionRequest> getUserAdoptionRequests(Long userId) {
        return adoptionRequestRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Get adoption request by ID
     */
    public AdoptionRequest getAdoptionRequestById(Long requestId) {
        return adoptionRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Adoption request not found with ID: " + requestId)
                );
    }

    /**
     * Approve adoption request
     */
    public AdoptionRequest approveAdoptionRequest(Long requestId) {

        AdoptionRequest request = getAdoptionRequestById(requestId);

        if (request.getStatus() != AdoptionRequest.Status.PENDING) {
            throw new RuntimeException("Only pending requests can be approved");
        }

        request.setStatus(AdoptionRequest.Status.APPROVED);

        // Mark pet as adopted
        Pet pet = request.getPet();
        pet.setStatus(Pet.AdoptionStatus.ADOPTED);
        petRepository.save(pet);

        System.out.println("Adoption request approved - ID: " + requestId);

        return adoptionRequestRepository.save(request);
    }

    /**
     * Reject adoption request
     */
    public AdoptionRequest rejectAdoptionRequest(Long requestId) {

        AdoptionRequest request = getAdoptionRequestById(requestId);

        if (request.getStatus() != AdoptionRequest.Status.PENDING) {
            throw new RuntimeException("Only pending requests can be rejected");
        }

        request.setStatus(AdoptionRequest.Status.REJECTED);

        System.out.println("Adoption request rejected - ID: " + requestId);

        return adoptionRequestRepository.save(request);
    }

    /**
     * Get requests for a pet
     */
    public List<AdoptionRequest> getPetAdoptionRequests(Long petId) {
        return adoptionRequestRepository.findByPetId(petId);
    }

    /**
     * Get all pending requests
     */
    public List<AdoptionRequest> getPendingAdoptionRequests() {
        return adoptionRequestRepository.findByStatus(
                AdoptionRequest.Status.PENDING
        );
    }
}
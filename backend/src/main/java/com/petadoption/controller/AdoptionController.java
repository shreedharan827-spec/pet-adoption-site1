package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.model.Adoption;
import com.petadoption.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Adoption Management
 * Handles HTTP requests for adoption-related operations
 */
@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdoptionController {

    private final AdoptionService adoptionService;

    /**
     * Create adoption request
     * POST /adoptions/request/{userId}/{petId}
     */
    @PostMapping("/request/{userId}/{petId}")
    public ResponseEntity<ApiResponse<Adoption>> createAdoptionRequest(
            @PathVariable Long userId,
            @PathVariable Long petId,
            @RequestParam(required = false) String adoptionReason) {
        try {
            System.out.println("[INFO] Creating adoption request - User: " + userId + ", Pet: " + petId);

            Adoption adoption = adoptionService.createAdoptionRequest(userId, petId, adoptionReason);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(true, "Adoption request created successfully", adoption, 201)
            );

        } catch (RuntimeException e) {
            System.out.println("[ERROR] Error creating adoption request: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null, 400)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Unexpected error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error creating adoption request", null, 500)
            );
        }
    }

    /**
     * Get user's adoption requests
     * GET /adoptions/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Adoption>>> getUserAdoptionRequests(
            @PathVariable Long userId) {
        try {
            List<Adoption> adoptions = adoptionService.getUserAdoptionRequests(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption requests fetched", adoptions, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching user adoptions: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error fetching requests", null, 500)
            );
        }
    }

    /**
     * Get pet's adoption requests
     * GET /adoptions/pet/{petId}
     */
    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<Adoption>>> getPetAdoptionRequests(
            @PathVariable Long petId) {
        try {
            List<Adoption> adoptions = adoptionService.getPetAdoptionRequests(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption requests fetched", adoptions, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pet adoptions: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error fetching requests", null, 500)
            );
        }
    }

    /**
     * Get adoption request by ID
     * GET /adoptions/{adoptionId}
     */
    @GetMapping("/{adoptionId}")
    public ResponseEntity<ApiResponse<Adoption>> getAdoptionById(
            @PathVariable Long adoptionId) {
        try {
            Adoption adoption = adoptionService.getAdoptionById(adoptionId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption request found", adoption, 200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Adoption not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Approve adoption request (Admin)
     * PUT /adoptions/{adoptionId}/approve
     */
    @PutMapping("/{adoptionId}/approve")
    public ResponseEntity<ApiResponse<Adoption>> approveAdoption(
            @PathVariable Long adoptionId,
            @RequestParam(required = false) String notes) {
        try {
            System.out.println("[INFO] Approving adoption: " + adoptionId);

            Adoption adoption = adoptionService.approveAdoption(adoptionId, notes);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption approved successfully", adoption, 200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Adoption not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Reject adoption request (Admin)
     * PUT /adoptions/{adoptionId}/reject
     */
    @PutMapping("/{adoptionId}/reject")
    public ResponseEntity<ApiResponse<Adoption>> rejectAdoption(
            @PathVariable Long adoptionId,
            @RequestParam String rejectionReason) {
        try {
            System.out.println("[INFO] Rejecting adoption: " + adoptionId);

            Adoption adoption = adoptionService.rejectAdoption(adoptionId, rejectionReason);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption rejected successfully", adoption, 200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Adoption not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Cancel adoption request
     * PUT /adoptions/{adoptionId}/cancel
     */
    @PutMapping("/{adoptionId}/cancel")
    public ResponseEntity<ApiResponse<Adoption>> cancelAdoption(
            @PathVariable Long adoptionId) {
        try {
            System.out.println("[INFO] Cancelling adoption: " + adoptionId);

            Adoption adoption = adoptionService.cancelAdoption(adoptionId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Adoption cancelled successfully", adoption, 200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Adoption not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Get all pending requests (Admin)
     * GET /adoptions/pending/all
     */
    @GetMapping("/pending/all")
    public ResponseEntity<ApiResponse<List<Adoption>>> getAllPendingRequests() {
        try {
            List<Adoption> adoptions = adoptionService.getAllPendingRequests();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pending requests fetched", adoptions, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pending requests: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error fetching requests", null, 500)
            );
        }
    }

    /**
     * Get adoption statistics (Admin)
     * GET /adoptions/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdoptionService.AdoptionStats>> getAdoptionStats() {
        try {
            AdoptionService.AdoptionStats stats = adoptionService.getAdoptionStats();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Statistics fetched", stats, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching stats: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error fetching statistics", null, 500)
            );
        }
    }
}
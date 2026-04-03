package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.model.AdoptionRequest;
import com.petadoption.service.AdoptionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Adoption Request Management
 * Handles adoption request submission and status updates
 */
@RestController
@RequestMapping("/adoption-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    /**
     * Submit a new adoption request
     * POST /adoption-requests
     * Requires: Authenticated user
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AdoptionRequest>> submitAdoptionRequest(
            @RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            Long petId = Long.parseLong(request.get("petId").toString());
            String message = (String) request.getOrDefault("message", "");

            System.out.println("[INFO] Processing adoption request submission - User: "
                    + userId + ", Pet: " + petId);

            AdoptionRequest adoptionRequest =
                    adoptionRequestService.submitAdoptionRequest(userId, petId, message);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(true,
                            "Adoption request submitted successfully",
                            adoptionRequest,
                            201)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Error submitting adoption request: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null, 400)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error submitting adoption request: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error submitting adoption request",
                            null,
                            500)
            );
        }
    }

    /**
     * Get all adoption requests for current user
     * GET /adoption-requests/user
     * Requires: Authenticated user
     */
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<AdoptionRequest>>> getUserAdoptionRequests(
            @RequestParam Long userId) {
        try {
            System.out.println("[INFO] Fetching adoption requests for user: " + userId);

            List<AdoptionRequest> requests =
                    adoptionRequestService.getUserAdoptionRequests(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "User adoption requests fetched successfully",
                            requests,
                            200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching user adoption requests: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error fetching adoption requests",
                            null,
                            500)
            );
        }
    }

    /**
     * Get adoption request by ID
     * GET /adoption-requests/{id}
     * Requires: Authenticated user
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AdoptionRequest>> getAdoptionRequest(
            @PathVariable Long id) {
        try {
            System.out.println("[INFO] Fetching adoption request: " + id);

            AdoptionRequest request =
                    adoptionRequestService.getAdoptionRequestById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Adoption request fetched successfully",
                            request,
                            200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Adoption request not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching adoption request: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error fetching adoption request",
                            null,
                            500)
            );
        }
    }

    /**
     * Approve an adoption request
     * PUT /adoption-requests/{id}/approve
     * Requires: ADMIN
     */
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdoptionRequest>> approveAdoptionRequest(
            @PathVariable Long id) {
        try {
            System.out.println("[INFO] Approving adoption request: " + id);

            AdoptionRequest approvedRequest =
                    adoptionRequestService.approveAdoptionRequest(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Adoption request approved successfully",
                            approvedRequest,
                            200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Error approving adoption request: "
                    + e.getMessage());

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(false, e.getMessage(), null, 404)
                );
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null, 400)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error approving adoption request: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error approving adoption request",
                            null,
                            500)
            );
        }
    }

    /**
     * Reject an adoption request
     * PUT /adoption-requests/{id}/reject
     * Requires: ADMIN
     */
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdoptionRequest>> rejectAdoptionRequest(
            @PathVariable Long id) {
        try {
            System.out.println("[INFO] Rejecting adoption request: " + id);

            AdoptionRequest rejectedRequest =
                    adoptionRequestService.rejectAdoptionRequest(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Adoption request rejected successfully",
                            rejectedRequest,
                            200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Error rejecting adoption request: "
                    + e.getMessage());

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(false, e.getMessage(), null, 404)
                );
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null, 400)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error rejecting adoption request: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error rejecting adoption request",
                            null,
                            500)
            );
        }
    }

    /**
     * Get adoption requests for a pet
     * GET /adoption-requests/pet/{petId}
     * Requires: ADMIN
     */
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdoptionRequest>>> getPetAdoptionRequests(
            @PathVariable Long petId) {
        try {
            System.out.println("[INFO] Fetching adoption requests for pet: " + petId);

            List<AdoptionRequest> requests =
                    adoptionRequestService.getPetAdoptionRequests(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Pet adoption requests fetched successfully",
                            requests,
                            200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pet adoption requests: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error fetching adoption requests",
                            null,
                            500)
            );
        }
    }

    /**
     * Get all pending adoption requests
     * GET /adoption-requests/pending
     * Requires: ADMIN
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdoptionRequest>>> getPendingAdoptionRequests() {
        try {
            System.out.println("[INFO] Fetching all pending adoption requests");

            List<AdoptionRequest> requests =
                    adoptionRequestService.getPendingAdoptionRequests();

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Pending adoption requests fetched successfully",
                            requests,
                            200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pending adoption requests: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Error fetching pending requests",
                            null,
                            500)
            );
        }
    }
}
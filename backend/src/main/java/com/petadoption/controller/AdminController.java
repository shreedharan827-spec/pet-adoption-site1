package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final PetService petService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Pet>>> getPendingPets() {
        try {
            List<Pet> pendingPets = petService.getPendingPets();

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Pending pets fetched successfully",
                            pendingPets,
                            200
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            false,
                            "Error fetching pending pets",
                            null,
                            500
                    )
            );
        }
    }

    @PutMapping("/{petId}/approve")
    public ResponseEntity<ApiResponse<Pet>> approvePet(
            @PathVariable Long petId
    ) {
        try {
            Pet approvedPet = petService.approvePet(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Pet approved successfully",
                            approvedPet,
                            200
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(
                            false,
                            e.getMessage(),
                            null,
                            400
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            false,
                            "Error approving pet",
                            null,
                            500
                    )
            );
        }
    }

    @PutMapping("/{petId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectPet(
            @PathVariable Long petId
    ) {
        try {
            petService.rejectPet(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Pet rejected successfully",
                            null,
                            200
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(
                            false,
                            e.getMessage(),
                            null,
                            400
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            false,
                            "Error rejecting pet",
                            null,
                            500
                    )
            );
        }
    }
}
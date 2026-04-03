package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Pet Management
 * Handles HTTP requests for pet-related operations
 */
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PetController {

    private final PetService petService;

    /** Get all available pets */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Pet>>> getAllAvailablePets() {
        try {
            List<Pet> pets = petService.getAllAvailablePets();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pets fetched successfully", pets, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pets: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error fetching pets", null, 500));
        }
    }

    /** Get pets by type */
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsByType(
            @PathVariable String type) {
        try {
            Pet.PetType petType = Pet.PetType.valueOf(type.toUpperCase());

            List<Pet> pets = petService.getPetsByType(petType);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pets fetched successfully", pets, 200)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid pet type", null, 400));

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pets by type: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error fetching pets", null, 500));
        }
    }

    /** Get pet by ID */
    @GetMapping("/{petId}")
    public ResponseEntity<ApiResponse<Pet>> getPetById(
            @PathVariable Long petId) {
        try {
            Pet pet = petService.getPetById(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pet found", pet, 200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Pet not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null, 404));
        }
    }

    /** Create/register new pet */
    @PostMapping
    public ResponseEntity<ApiResponse<Pet>> createPet(@RequestBody Pet pet) {
        try {
            Pet createdPet = petService.createPet(pet);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true,
                            "Pet created successfully",
                            createdPet,
                            201));

        } catch (Exception e) {
            System.out.println("[ERROR] Error creating pet: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error creating pet", null, 500));
        }
    }

    /** Update pet by ID */
    @PutMapping("/{petId}")
    public ResponseEntity<ApiResponse<Pet>> updatePet(
            @PathVariable Long petId,
            @RequestBody Pet petDetails) {
        try {
            Pet updatedPet = petService.updatePet(petId, petDetails);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Pet updated successfully",
                            updatedPet,
                            200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Pet not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null, 404));
        }
    }

    /** Delete pet by ID */
    @DeleteMapping("/{petId}")
    public ResponseEntity<ApiResponse<Void>> deletePet(
            @PathVariable Long petId) {
        try {
            petService.deletePet(petId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "Pet deleted successfully",
                            null,
                            200)
            );

        } catch (RuntimeException e) {
            System.out.println("[WARN] Pet not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null, 404));

        } catch (Exception e) {
            System.out.println("[ERROR] Error deleting pet: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error deleting pet", null, 500));
        }
    }

    /** Get available pets count */
    @GetMapping("/stats/count")
    public ResponseEntity<ApiResponse<Long>> getAvailablePetsCount() {
        try {
            long count = petService.getAvailablePetsCount();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pet count fetched", count, 200)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Error fetching pet count: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error fetching count", null, 500));
        }
    }
}
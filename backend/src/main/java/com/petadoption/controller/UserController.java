package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.dto.RegistrationRequest;
import com.petadoption.dto.UserResponse;
import com.petadoption.exception.UserAlreadyExistsException;
import com.petadoption.exception.UserNotFoundException;
import com.petadoption.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User Management
 * Handles HTTP requests for user-related operations
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    /**
     * Register a new user
     * POST /users/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody RegistrationRequest request) {
        try {
            System.out.println("[INFO] Registering new user with email: "
                    + request.getEmail());

            UserResponse userResponse = userService.registerUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(true,
                            "User registered successfully",
                            userResponse,
                            201)
            );

        } catch (UserAlreadyExistsException e) {
            System.out.println("[WARN] Registration failed - User already exists: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>(false, e.getMessage(), null, 409)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Registration failed with error: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Registration failed: " + e.getMessage(),
                            null,
                            500)
            );
        }
    }

    /**
     * Get user by ID
     * GET /users/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long userId) {
        try {
            UserResponse userResponse = userService.getUserById(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "User found", userResponse, 200)
            );

        } catch (UserNotFoundException e) {
            System.out.println("[WARN] User not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Get user by email
     * GET /users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(
            @PathVariable String email) {
        try {
            UserResponse userResponse = userService.getUserByEmail(email);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "User found", userResponse, 200)
            );

        } catch (UserNotFoundException e) {
            System.out.println("[WARN] User not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Update user
     * PUT /users/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody RegistrationRequest request) {
        try {
            System.out.println("[INFO] Updating user with ID: " + userId);

            UserResponse userResponse =
                    userService.updateUser(userId, request);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "User updated successfully",
                            userResponse,
                            200)
            );

        } catch (UserNotFoundException e) {
            System.out.println("[WARN] User not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );

        } catch (Exception e) {
            System.out.println("[ERROR] Update failed with error: "
                    + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false,
                            "Update failed: " + e.getMessage(),
                            null,
                            500)
            );
        }
    }

    /**
     * Verify user
     * POST /users/{userId}/verify
     */
    @PostMapping("/{userId}/verify")
    public ResponseEntity<ApiResponse<UserResponse>> verifyUser(
            @PathVariable Long userId) {
        try {
            System.out.println("[INFO] Verifying user with ID: " + userId);

            UserResponse userResponse = userService.verifyUser(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "User verified successfully",
                            userResponse,
                            200)
            );

        } catch (UserNotFoundException e) {
            System.out.println("[WARN] User not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Delete user
     * DELETE /users/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId) {
        try {
            System.out.println("[INFO] Deleting user with ID: " + userId);

            userService.deleteUser(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "User deleted successfully",
                            null,
                            200)
            );

        } catch (UserNotFoundException e) {
            System.out.println("[WARN] User not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null, 404)
            );
        }
    }

    /**
     * Health check endpoint
     * GET /users/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
                new ApiResponse<>(true,
                        "User service is healthy",
                        "OK",
                        200)
        );
    }
}
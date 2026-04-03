package com.petadoption.service;

import com.petadoption.dto.RegistrationRequest;
import com.petadoption.dto.UserResponse;
import com.petadoption.exception.UserAlreadyExistsException;
import com.petadoption.exception.UserNotFoundException;
import com.petadoption.model.User;
import com.petadoption.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * Service for User Management
 * Handles business logic for user registration and operations
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;

    /**
     * Register a new user
     */
    public UserResponse registerUser(RegistrationRequest request) {
        System.out.println("[INFO] Attempting to register user with email: " + request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }

        if (userRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new UserAlreadyExistsException("Aadhar number already registered");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .aadharNumber(request.getAadharNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .securityQuestion1(request.getSecurityQuestion1())
                .securityAnswer1(request.getSecurityAnswer1())
                .securityQuestion2(request.getSecurityQuestion2())
                .securityAnswer2(request.getSecurityAnswer2())
                .isVerified(false)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        System.out.println("[INFO] User registered successfully with ID: " + savedUser.getId());

        return mapToUserResponse(savedUser);
    }

    /**
     * Get user by ID
     */
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToUserResponse(user);
    }

    /**
     * Get user by email
     */
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToUserResponse(user);
    }

    /**
     * Update user
     */
    public UserResponse updateUser(Long userId, RegistrationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        user.setSecurityQuestion1(request.getSecurityQuestion1());
        user.setSecurityAnswer1(request.getSecurityAnswer1());
        user.setSecurityQuestion2(request.getSecurityQuestion2());
        user.setSecurityAnswer2(request.getSecurityAnswer2());

        User updatedUser = userRepository.save(user);

        System.out.println("[INFO] User updated successfully with ID: " + userId);

        return mapToUserResponse(updatedUser);
    }

    /**
     * Verify user
     */
    public UserResponse verifyUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setIsVerified(true);

        User verifiedUser = userRepository.save(user);

        System.out.println("[INFO] User verified successfully with ID: " + userId);

        return mapToUserResponse(verifiedUser);
    }

    /**
     * Send OTP to user
     */
    public String sendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String otp = String.format("%06d", (int)(Math.random() * 1000000));
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        System.out.println("[INFO] OTP for " + email + " is: " + otp + " (valid 10 minutes)");
        
        // Send OTP via SMS to registered phone
        smsService.sendOtpSms(user.getPhoneNumber(), otp);
        
        return otp;
    }

    /**
     * Verify OTP for user
     */
    public UserResponse verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getOtpCode() == null || user.getOtpExpiry() == null
                || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired or not requested");
        }

        if (!user.getOtpCode().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setIsVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);

        User updatedUser = userRepository.save(user);

        System.out.println("[INFO] OTP verified for user " + email);
        return mapToUserResponse(updatedUser);
    }

    /**
     * Delete user
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);

        System.out.println("[INFO] User deleted successfully with ID: " + userId);
    }

    /**
     * Map User entity to UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .isVerified(user.getIsVerified())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
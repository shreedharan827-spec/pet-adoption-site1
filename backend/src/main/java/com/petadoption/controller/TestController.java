package com.petadoption.controller;

import com.petadoption.dto.ApiResponse;
import com.petadoption.service.SmsService;
import com.petadoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Test Controller for Development/Testing
 * Provides test endpoints for SMS OTP functionality
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    private final UserService userService;
    private final SmsService smsService;

    /**
     * Send OTP to a specific phone number (for testing)
     * GET /test/send-otp-to-phone/{phoneNumber}
     */
    @GetMapping("/send-otp-to-phone/{phoneNumber}")
    public ResponseEntity<ApiResponse<String>> sendOtpToPhone(
            @PathVariable String phoneNumber) {
        try {
            // Generate 6-digit OTP
            String otp = String.format("%06d", (int)(Math.random() * 1000000));
            
            System.out.println("[TEST] Generated OTP: " + otp + " for phone: " + phoneNumber);
            
            // Send OTP via SMS
            smsService.sendOtpSms(phoneNumber, otp);
            
            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            "OTP sent successfully to " + phoneNumber + ". OTP (for dev): " + otp,
                            otp,
                            200)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false,
                            "Failed to send OTP: " + e.getMessage(),
                            null,
                            400)
            );
        }
    }

    /**
     * Health check endpoint
     * GET /test/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Test endpoints are active", "OK", 200)
        );
    }
}
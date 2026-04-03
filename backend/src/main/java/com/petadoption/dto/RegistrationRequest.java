package com.petadoption.dto;

import lombok.*;
import jakarta.validation.constraints.*;

/**
 * DTO for User Registration Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits starting with 6-9")
    private String phoneNumber;

    @NotBlank(message = "Aadhar number is required")
    @Pattern(regexp = "^\\d{12}$", message = "Aadhar number must be 12 digits")
    private String aadharNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Security question 1 is required")
    private String securityQuestion1;

    @NotBlank(message = "Security answer 1 is required")
    private String securityAnswer1;

    @NotBlank(message = "Security question 2 is required")
    private String securityQuestion2;

    @NotBlank(message = "Security answer 2 is required")
    private String securityAnswer2;
}

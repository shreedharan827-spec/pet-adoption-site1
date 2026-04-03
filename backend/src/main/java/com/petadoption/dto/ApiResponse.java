package com.petadoption.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * API Response Wrapper
 * Standard response format for all API endpoints
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;
    private Integer statusCode;
    private Long timestamp;

    public ApiResponse(Boolean success, String message, T data, Integer statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
    }
}

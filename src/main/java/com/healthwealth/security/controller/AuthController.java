package com.healthwealth.security.controller;

import com.healthwealth.security.dto.AuthRequest;
import com.healthwealth.security.dto.AuthResponse;
import com.healthwealth.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for authentication")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and get token")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh authentication token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
} 
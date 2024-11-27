package com.healthwealth.user.controller;

import com.healthwealth.security.util.SecurityUtils;
import com.healthwealth.user.dto.UserProfileResponse;
import com.healthwealth.user.dto.UserProfileUpdateRequest;
import com.healthwealth.user.dto.UserRegistrationRequest;
import com.healthwealth.user.entity.User;
import com.healthwealth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<UserProfileResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(UserProfileResponse.fromUser(user));
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(UserProfileResponse.fromUser(user));
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UserProfileUpdateRequest request) {
        User user = userService.updateProfile(SecurityUtils.getCurrentUserId(), request);
        return ResponseEntity.ok(UserProfileResponse.fromUser(user));
    }

    @PutMapping("/health-conditions")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update health conditions")
    public ResponseEntity<Void> updateHealthConditions(@RequestBody Set<String> conditions) {
        userService.updateHealthConditions(SecurityUtils.getCurrentUserId(), conditions);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/preferences")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user preferences")
    public ResponseEntity<Void> updatePreferences(@RequestBody Map<String, String> preferences) {
        userService.updatePreferences(SecurityUtils.getCurrentUserId(), preferences);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/notifications/{enabled}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Toggle notifications")
    public ResponseEntity<Void> toggleNotifications(@PathVariable boolean enabled) {
        userService.toggleNotifications(SecurityUtils.getCurrentUserId(), enabled);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change password")
    public ResponseEntity<Void> changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(SecurityUtils.getCurrentUserId(), currentPassword, newPassword);
        return ResponseEntity.ok().build();
    }
} 
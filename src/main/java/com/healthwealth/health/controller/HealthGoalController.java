package com.healthwealth.health.controller;

import com.healthwealth.health.entity.HealthGoal;
import com.healthwealth.health.service.HealthGoalService;
import com.healthwealth.security.util.SecurityUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health/goals")
@RequiredArgsConstructor
@Tag(name = "Health Goals", description = "APIs for managing health goals")
public class HealthGoalController {
    private final HealthGoalService healthGoalService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a health goal")
    public ResponseEntity<HealthGoal> createGoal(@RequestBody HealthGoal goal) {
        return ResponseEntity.ok(healthGoalService.createGoal(goal));
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get active goals")
    public ResponseEntity<List<HealthGoal>> getActiveGoals() {
        return ResponseEntity.ok(healthGoalService.findActiveGoalsByUserId(getCurrentUserId()));
    }

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 
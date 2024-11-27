package com.healthwealth.health.controller;

import com.healthwealth.health.entity.HealthActivity;
import com.healthwealth.health.service.HealthActivityService;
import com.healthwealth.security.util.SecurityUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/health/activities")
@RequiredArgsConstructor
@Tag(name = "Health Activity", description = "APIs for tracking health activities")
public class HealthActivityController {
    private final HealthActivityService healthActivityService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Log a health activity")
    public ResponseEntity<HealthActivity> logActivity(@RequestBody HealthActivity activity) {
        return ResponseEntity.ok(healthActivityService.logActivity(activity));
    }

    @GetMapping("/daily")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get daily activity")
    public ResponseEntity<HealthActivity> getDailyActivity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(healthActivityService.getDailyActivity(getCurrentUserId(), date));
    }

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get activity history")
    public ResponseEntity<Page<HealthActivity>> getActivityHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(healthActivityService.getActivityHistory(
            getCurrentUserId(), startDate, endDate, pageable));
    }

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 
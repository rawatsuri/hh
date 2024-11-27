package com.healthwealth.notification.controller;

import com.healthwealth.notification.entity.Notification;
import com.healthwealth.notification.entity.Notification.NotificationType;
import com.healthwealth.notification.entity.Notification.PriorityLevel;
import com.healthwealth.notification.service.NotificationService;
import com.healthwealth.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "APIs for managing notifications")
public class NotificationController {
    
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user notifications")
    public ResponseEntity<Page<Notification>> getNotifications(Pageable pageable) {
        return ResponseEntity.ok(notificationService.getUserNotifications(
            SecurityUtils.getCurrentUserId(), pageable));
    }

    @GetMapping("/unread-count")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get unread notifications count")
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(notificationService.getUnreadCount(
            SecurityUtils.getCurrentUserId()));
    }

    @GetMapping("/unread/priority/{priority}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get unread notifications by priority")
    public ResponseEntity<List<Notification>> getUnreadByPriority(
            @PathVariable PriorityLevel priority) {
        return ResponseEntity.ok(notificationService.getUnreadHighPriorityNotifications(
            SecurityUtils.getCurrentUserId(), priority));
    }

    @GetMapping("/unread/type/{type}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get unread notifications by type")
    public ResponseEntity<List<Notification>> getUnreadByType(
            @PathVariable NotificationType type) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsByType(
            SecurityUtils.getCurrentUserId(), type));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id, SecurityUtils.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/read-all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<Void> markAllAsRead() {
        notificationService.markAllAsRead(SecurityUtils.getCurrentUserId());
        return ResponseEntity.ok().build();
    }
} 
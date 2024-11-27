// package com.healthwealth.notification.service;

// import lombok.RequiredArgsConstructor;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

// import com.healthwealth.notification.exception.NotificationException;

// import java.util.concurrent.CompletableFuture;

// @Service
// @RequiredArgsConstructor
// public class PushNotificationService {
//     private final FCMService fcmService;
    
//     @Async
//     public CompletableFuture<Void> sendPushNotification(Long userId, String message) {
//         return CompletableFuture.runAsync(() -> {
//             try {
//                 fcmService.sendNotification(userId, message);
//             } catch (Exception e) {
//                 throw new NotificationException("Failed to send push notification", e);
//             }
//         });
//     }
// } 
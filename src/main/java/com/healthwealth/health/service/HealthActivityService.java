package com.healthwealth.health.service;

import com.healthwealth.common.exception.ResourceNotFoundException;
import com.healthwealth.health.entity.HealthActivity;
import com.healthwealth.health.entity.HealthGoal;
import com.healthwealth.health.repository.HealthActivityRepository;
import com.healthwealth.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class HealthActivityService {
    private final HealthActivityRepository healthActivityRepository;
    private final HealthGoalService healthGoalService;
    @SuppressWarnings("unused")
    private final NotificationService notificationService;

    @Transactional
    @CacheEvict(value = "healthActivities", key = "#activity.user.id + '_' + #activity.activityDate")
    public HealthActivity logActivity(HealthActivity activity) {
        HealthActivity saved = healthActivityRepository.save(activity);
        
        // Async goal check and notification
        CompletableFuture.runAsync(() -> {
            checkGoalsAndNotify(saved);
        });
        
        return saved;
    }

    @Cacheable(value = "healthActivities", key = "#userId + '_' + #date")
    @Transactional(readOnly = true)
    public HealthActivity getDailyActivity(Long userId, LocalDate date) {
        return healthActivityRepository.findByUserIdAndDate(userId, date)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
    }

    @Transactional(readOnly = true)
    public Page<HealthActivity> getActivityHistory(
            Long userId, 
            LocalDate startDate, 
            LocalDate endDate, 
            Pageable pageable) {
        return healthActivityRepository.findByUserIdAndDateRange(
            userId, startDate, endDate, pageable);
    }

    private void checkGoalsAndNotify(HealthActivity activity) {
        healthGoalService.findActiveGoalsByUserId(activity.getUser().getId())
            .forEach(goal -> {
                if (isGoalAchieved(activity, goal)) {
                    notificationService.sendGoalAchievementNotification(
                        activity.getUser(), goal);
                }
            });
    }

    @SuppressWarnings("unused")
    private boolean isGoalAchieved(HealthActivity activity, HealthGoal goal) {
        return switch (goal.getGoalType()) {
            case DAILY_STEPS -> activity.getSteps() >= goal.getTargetValue();
            case WATER_INTAKE -> activity.getWaterIntake() >= goal.getTargetValue();
            case SLEEP_HOURS -> activity.getSleepHours() >= goal.getTargetValue();
            case CALORIES_BURNED -> activity.getCaloriesBurned() >= goal.getTargetValue();
        };
    }
} 
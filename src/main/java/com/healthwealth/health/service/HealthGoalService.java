package com.healthwealth.health.service;

import com.healthwealth.health.entity.HealthActivity;
import com.healthwealth.health.entity.HealthGoal;
import com.healthwealth.health.repository.HealthGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthGoalService {
    private final HealthGoalRepository healthGoalRepository;

    @Cacheable(value = "activeGoals", key = "#userId")
    @Transactional(readOnly = true)
    public List<HealthGoal> findActiveGoalsByUserId(Long userId) {
        return healthGoalRepository.findActiveGoalsByUserId(userId);
    }

    @CacheEvict(value = "activeGoals", key = "#goal.user.id")
    @Transactional
    public HealthGoal createGoal(HealthGoal goal) {
        // Deactivate any existing active goals of the same type
        healthGoalRepository.findActiveGoalByUserIdAndType(goal.getUser().getId(), goal.getGoalType())
            .ifPresent(existingGoal -> {
                existingGoal.setActive(false);
                healthGoalRepository.save(existingGoal);
            });
        
        return healthGoalRepository.save(goal);
    }

    public boolean isGoalAchieved(HealthActivity activity, HealthGoal goal) {
        return switch (goal.getGoalType()) {
            case DAILY_STEPS -> activity.getSteps() >= goal.getTargetValue();
            case WATER_INTAKE -> activity.getWaterIntake() >= goal.getTargetValue();
            case SLEEP_HOURS -> activity.getSleepHours() >= goal.getTargetValue();
            case CALORIES_BURNED -> activity.getCaloriesBurned() >= goal.getTargetValue();
        };
    }
} 
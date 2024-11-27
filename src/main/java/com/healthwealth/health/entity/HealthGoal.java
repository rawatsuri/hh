package com.healthwealth.health.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "health_goals")
@Getter
@Setter
public class HealthGoal extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalType goalType;
    
    private Double targetValue;
    
    private boolean active = true;
    
    public enum GoalType {
        DAILY_STEPS,
        WATER_INTAKE,
        SLEEP_HOURS,
        CALORIES_BURNED
    }
} 
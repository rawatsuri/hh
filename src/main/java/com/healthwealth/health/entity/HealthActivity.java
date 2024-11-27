package com.healthwealth.health.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "health_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthActivity extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;
    
    @Column(nullable = false)
    private Integer steps;
    
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    
    @Column(name = "water_intake")
    private Double waterIntake;
    
    @Column(name = "sleep_hours")
    private Double sleepHours;
    
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    private String notes;
} 
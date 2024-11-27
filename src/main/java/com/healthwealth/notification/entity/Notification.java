package com.healthwealth.notification.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications", indexes = {
    @Index(name = "idx_user_read", columnList = "user_id,read"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Getter
@Setter
public class Notification extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String message;
    
    @Column(nullable = false)
    private boolean read = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    
    @Column(name = "priority_level")
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel = PriorityLevel.NORMAL;
    
    @Column(name = "action_url")
    private String actionUrl;
    
    public enum NotificationType {
        // Health Related
        GOAL_ACHIEVED,
        GOAL_REMINDER,
        ACTIVITY_INSIGHT,
        HEALTH_TREND_ALERT,
        WORKOUT_RECOMMENDATION,
        SLEEP_PATTERN_INSIGHT,
        HYDRATION_REMINDER,
        
        // Wealth Related
        BUDGET_EXCEEDED,
        SPENDING_PATTERN_ALERT,
        SAVING_OPPORTUNITY,
        INVESTMENT_RECOMMENDATION,
        UNUSUAL_EXPENSE_ALERT,
        BUDGET_MILESTONE,
        
        // AI Insights
        AI_HEALTH_PREDICTION,
        AI_WEALTH_PREDICTION,
        AI_LIFESTYLE_RECOMMENDATION,
        AI_CORRELATION_INSIGHT,    // Correlations between health and wealth patterns
        AI_GOAL_RECOMMENDATION,
        AI_RISK_ALERT,
        
        // System
        SYSTEM_UPDATE,
        FEATURE_ANNOUNCEMENT,
        MAINTENANCE_ALERT
    }
    
    public enum PriorityLevel {
        LOW,
        NORMAL,
        HIGH,
        URGENT
    }
} 
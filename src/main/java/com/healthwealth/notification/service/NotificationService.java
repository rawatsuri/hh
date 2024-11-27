package com.healthwealth.notification.service;

import com.healthwealth.health.entity.HealthGoal;
import com.healthwealth.notification.entity.Notification;
import com.healthwealth.notification.entity.Notification.NotificationType;
import com.healthwealth.notification.entity.Notification.PriorityLevel;
import com.healthwealth.notification.repository.NotificationRepository;
import com.healthwealth.user.entity.User;
import com.healthwealth.wealth.entity.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendAIHealthPrediction(User user, String prediction, String explanation) {
        createNotification(user, "Health Prediction", prediction,
            NotificationType.AI_HEALTH_PREDICTION, PriorityLevel.HIGH, explanation);
    }

    @Transactional
    public void sendAIWealthPrediction(User user, String prediction, String explanation) {
        createNotification(user, "Financial Prediction", prediction,
            NotificationType.AI_WEALTH_PREDICTION, PriorityLevel.HIGH, explanation);
    }

    @Transactional
    public void sendAICorrelationInsight(User user, String insight) {
        createNotification(user, "Health & Wealth Insight", insight,
            NotificationType.AI_CORRELATION_INSIGHT, PriorityLevel.NORMAL, null);
    }

    @Transactional
    public void sendAILifestyleRecommendation(User user, String recommendation) {
        createNotification(user, "Lifestyle Recommendation", recommendation,
            NotificationType.AI_LIFESTYLE_RECOMMENDATION, PriorityLevel.NORMAL, null);
    }

    @Transactional
    public void sendHealthTrendAlert(User user, String trend, String analysis) {
        createNotification(user, "Health Trend Alert", trend,
            NotificationType.HEALTH_TREND_ALERT, PriorityLevel.HIGH, null);
    }

    @Transactional
    public void sendSpendingPatternAlert(User user, String pattern, String recommendation) {
        createNotification(user, "Spending Pattern Alert", pattern,
            NotificationType.SPENDING_PATTERN_ALERT, PriorityLevel.HIGH, null);
    }

    @Transactional
    public void sendGoalAchievementNotification(User user, HealthGoal goal) {
        createNotification(user, "Goal Achieved!", 
            "Congratulations! You've achieved your " + goal.getGoalType().name() + " goal!",
            NotificationType.GOAL_ACHIEVED, PriorityLevel.HIGH, null);
    }

    @Transactional
    public void sendBudgetExceededNotification(User user, Budget budget, BigDecimal currentAmount) {
        createNotification(user, "Budget Alert",
            "Warning: You've exceeded your budget for " + budget.getCategory().getName() 
            + ". Current spending: " + currentAmount,
            NotificationType.BUDGET_EXCEEDED, PriorityLevel.URGENT, null);
    }

    private void createNotification(User user, String title, String message, 
            NotificationType type, PriorityLevel priority, String actionUrl) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setPriorityLevel(priority);
        notification.setActionUrl(actionUrl);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public Page<Notification> getUserNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadHighPriorityNotifications(Long userId) {
        return notificationRepository.findUnreadByUserIdAndPriorityLevel(
            userId, PriorityLevel.HIGH);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        notificationRepository.findById(notificationId)
            .filter(n -> n.getUser().getId().equals(userId))
            .ifPresent(notification -> {
                notification.setRead(true);
                notificationRepository.save(notification);
            });
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotificationsByType(Long userId, NotificationType type) {
        return notificationRepository.findUnreadByUserIdAndType(userId, type);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        Page<Notification> notifications = getUserNotifications(userId, Pageable.unpaged());
        notifications.forEach(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadHighPriorityNotifications(Long userId, PriorityLevel priority) {
        return notificationRepository.findUnreadByUserIdAndPriorityLevel(userId, priority);
    }
} 
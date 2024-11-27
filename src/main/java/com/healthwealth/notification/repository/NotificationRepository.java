package com.healthwealth.notification.repository;

import com.healthwealth.notification.entity.Notification;
import com.healthwealth.notification.entity.Notification.PriorityLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.createdAt DESC")
    Page<Notification> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.read = false")
    long countUnreadByUserId(Long userId);
    
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
           "AND n.read = false AND n.priorityLevel = :priorityLevel " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findUnreadByUserIdAndPriorityLevel(Long userId, PriorityLevel priorityLevel);
    
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
           "AND n.read = false AND n.type = :type " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findUnreadByUserIdAndType(Long userId, Notification.NotificationType type);
} 
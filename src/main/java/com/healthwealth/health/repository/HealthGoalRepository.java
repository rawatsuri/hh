package com.healthwealth.health.repository;

import java.util.List;
import java.util.Optional;
import com.healthwealth.health.entity.HealthGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthGoalRepository extends JpaRepository<HealthGoal, Long> {
    
    @Query("SELECT hg FROM HealthGoal hg WHERE hg.user.id = :userId AND hg.active = true")
    List<HealthGoal> findActiveGoalsByUserId(Long userId);
    
    @Query("SELECT hg FROM HealthGoal hg WHERE hg.user.id = :userId " +
           "AND hg.goalType = :goalType AND hg.active = true")
    Optional<HealthGoal> findActiveGoalByUserIdAndType(Long userId, HealthGoal.GoalType goalType);
} 
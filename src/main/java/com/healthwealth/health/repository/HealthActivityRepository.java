package com.healthwealth.health.repository;

import com.healthwealth.health.entity.HealthActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HealthActivityRepository extends JpaRepository<HealthActivity, Long> {
    
    @Query("SELECT ha FROM HealthActivity ha WHERE ha.user.id = :userId " +
           "AND ha.activityDate BETWEEN :startDate AND :endDate")
    Page<HealthActivity> findByUserIdAndDateRange(
        Long userId, 
        LocalDate startDate, 
        LocalDate endDate, 
        Pageable pageable
    );
    
    @Query("SELECT ha FROM HealthActivity ha WHERE ha.user.id = :userId " +
           "AND ha.activityDate = :date")
    Optional<HealthActivity> findByUserIdAndDate(Long userId, LocalDate date);
    
    @Query("SELECT AVG(ha.steps) FROM HealthActivity ha WHERE ha.user.id = :userId " +
           "AND ha.activityDate BETWEEN :startDate AND :endDate")
    Double getAverageSteps(Long userId, LocalDate startDate, LocalDate endDate);
} 
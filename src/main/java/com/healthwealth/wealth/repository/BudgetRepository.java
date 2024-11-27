package com.healthwealth.wealth.repository;

import com.healthwealth.wealth.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    @Query("SELECT b FROM Budget b LEFT JOIN FETCH b.category " +
           "WHERE b.user.id = :userId AND b.budgetPeriod = :period")
    List<Budget> findByUserIdAndPeriod(Long userId, YearMonth period);
    
    @Query("SELECT b FROM Budget b LEFT JOIN FETCH b.category " +
           "WHERE b.user.id = :userId AND b.category.id = :categoryId " +
           "AND b.budgetPeriod = :period")
    Optional<Budget> findByUserIdAndCategoryIdAndPeriod(
        Long userId,
        Long categoryId,
        YearMonth period
    );
} 
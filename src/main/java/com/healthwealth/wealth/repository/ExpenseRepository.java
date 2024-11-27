package com.healthwealth.wealth.repository;

import com.healthwealth.wealth.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    @Query("SELECT e FROM Expense e LEFT JOIN FETCH e.category " +
           "WHERE e.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate")
    Page<Expense> findByUserIdAndDateRange(
        Long userId, 
        LocalDateTime startDate, 
        LocalDateTime endDate, 
        Pageable pageable
    );
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId " +
           "AND e.category.id = :categoryId AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserIdAndCategoryIdAndDateRange(
        Long userId,
        Long categoryId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
    
    @Query("SELECT e.category.name, SUM(e.amount) FROM Expense e " +
           "WHERE e.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate " +
           "GROUP BY e.category.name")
    List<Object[]> getExpenseSummaryByCategory(
        Long userId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
} 
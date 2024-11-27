package com.healthwealth.wealth.service;

import com.healthwealth.notification.service.NotificationService;
import com.healthwealth.wealth.entity.Expense;
import com.healthwealth.wealth.repository.ExpenseRepository;
import com.healthwealth.wealth.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final NotificationService notificationService;

    @Transactional
    @CacheEvict(value = {"expenseSummaries", "categoryExpenses"}, key = "#expense.user.id")
    public Expense logExpense(Expense expense) {
        Expense saved = expenseRepository.save(expense);
        
        // Async budget check and notification
        CompletableFuture.runAsync(() -> {
            checkBudgetAndNotify(saved);
        });
        
        return saved;
    }

    @Cacheable(value = "expenseSummaries", key = "#userId + '_' + #yearMonth")
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getMonthlyExpenseSummary(Long userId, YearMonth yearMonth) {
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        
        return expenseRepository.getExpenseSummaryByCategory(userId, startDate, endDate)
            .stream()
            .collect(Collectors.toMap(
                row -> (String) row[0],
                row -> (BigDecimal) row[1]
            ));
    }

    @Transactional(readOnly = true)
    public Page<Expense> getExpenseHistory(
            Long userId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        return expenseRepository.findByUserIdAndDateRange(
            userId, startDate, endDate, pageable);
    }

    private void checkBudgetAndNotify(Expense expense) {
        budgetRepository.findByUserIdAndCategoryIdAndPeriod(
                expense.getUser().getId(),
                expense.getCategory().getId(),
                YearMonth.from(expense.getExpenseDate()))
            .ifPresent(budget -> {
                BigDecimal totalExpenses = expenseRepository
                    .sumByUserIdAndCategoryIdAndDateRange(
                        expense.getUser().getId(),
                        expense.getCategory().getId(),
                        YearMonth.from(expense.getExpenseDate()).atDay(1).atStartOfDay(),
                        YearMonth.from(expense.getExpenseDate()).atEndOfMonth().atTime(23, 59, 59));
                
                if (totalExpenses.compareTo(budget.getAmount()) > 0) {
                    notificationService.sendBudgetExceededNotification(
                        expense.getUser(), budget, totalExpenses);
                }
            });
    }
} 
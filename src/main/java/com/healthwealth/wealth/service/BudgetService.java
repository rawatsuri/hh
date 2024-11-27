package com.healthwealth.wealth.service;

import com.healthwealth.common.exception.ResourceNotFoundException;
import com.healthwealth.wealth.entity.Budget;
import com.healthwealth.wealth.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    
    private final BudgetRepository budgetRepository;

    @Transactional
    @CacheEvict(value = "budgets", key = "#budget.user.id + '_' + #budget.budgetPeriod")
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Cacheable(value = "budgets", key = "#userId + '_' + #period")
    @Transactional(readOnly = true)
    public List<Budget> findByUserIdAndPeriod(Long userId, YearMonth period) {
        return budgetRepository.findByUserIdAndPeriod(userId, period);
    }
} 
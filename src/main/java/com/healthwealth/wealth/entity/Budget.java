package com.healthwealth.wealth.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "budgets", indexes = {
    @Index(name = "idx_user_period", columnList = "user_id,budget_period")
})
@Getter
@Setter
public class Budget extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;
    
    @Column(nullable = false)
    private YearMonth budgetPeriod;
    
    @Column(nullable = false)
    private BigDecimal amount;
} 
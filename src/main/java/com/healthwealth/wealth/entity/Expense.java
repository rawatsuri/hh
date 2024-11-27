package com.healthwealth.wealth.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses", indexes = {
    @Index(name = "idx_user_date", columnList = "user_id,expense_date"),
    @Index(name = "idx_category", columnList = "category_id")
})
@Getter
@Setter
public class Expense extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDateTime expenseDate;
    
    private String description;
} 
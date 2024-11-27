package com.healthwealth.wealth.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "expense_categories")
@Getter
@Setter
public class ExpenseCategory extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    private boolean isDefault;
} 
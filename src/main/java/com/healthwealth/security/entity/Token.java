package com.healthwealth.security.entity;

import com.healthwealth.common.entity.BaseEntity;
import com.healthwealth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class Token extends BaseEntity {
    
    @Column(unique = true)
    private String token;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
} 
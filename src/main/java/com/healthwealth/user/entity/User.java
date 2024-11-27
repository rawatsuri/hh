package com.healthwealth.user.entity;

import com.healthwealth.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private String phoneNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(name = "height_cm")
    private Integer heightCm;
    
    @Column(name = "weight_kg")
    private Double weightKg;
    
    @Column(name = "activity_level")
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;
    
    @ElementCollection
    @CollectionTable(name = "user_health_conditions", 
                    joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "condition_name")
    private Set<String> healthConditions = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "user_preferences", 
                    joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "preference_key")
    @Column(name = "preference_value")
    private Map<String, String> preferences = new HashMap<>();
    
    private boolean enabled = true;
    
    @Column(name = "notification_enabled")
    private boolean notificationEnabled = true;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum ActivityLevel {
        SEDENTARY,
        LIGHTLY_ACTIVE,
        MODERATELY_ACTIVE,
        VERY_ACTIVE,
        EXTREMELY_ACTIVE
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
} 
package com.healthwealth.user.dto;

import com.healthwealth.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private User.Gender gender;
    private Integer heightCm;
    private Double weightKg;
    private User.ActivityLevel activityLevel;
    private Set<String> healthConditions;
    private Map<String, String> preferences;
    private boolean notificationEnabled;
    
    public static UserProfileResponse fromUser(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .heightCm(user.getHeightCm())
                .weightKg(user.getWeightKg())
                .activityLevel(user.getActivityLevel())
                .healthConditions(user.getHealthConditions())
                .preferences(user.getPreferences())
                .notificationEnabled(user.isNotificationEnabled())
                .build();
    }
} 
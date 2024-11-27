package com.healthwealth.user.repository;



import com.healthwealth.user.entity.User;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;



import java.time.LocalDateTime;

import java.util.Optional;



@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    

    @Query("SELECT u FROM User u WHERE u.email = :email")

    Optional<User> findByEmail(String email);

    

    @Query("SELECT u FROM User u WHERE u.email = :email")

    Optional<User> findByEmailWithRoles(String email);

    

    boolean existsByEmail(String email);

    

    @Query("SELECT u FROM User u WHERE u.activityLevel = :activityLevel")

    Page<User> findByActivityLevel(User.ActivityLevel activityLevel, Pageable pageable);

    

    @Modifying

    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :userId")

    void updateLastLogin(Long userId, LocalDateTime lastLogin);

    

    @Query("SELECT u FROM User u WHERE u.notificationEnabled = true")

    Page<User> findUsersWithNotificationsEnabled(Pageable pageable);

    

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :since")

    long countNewUsersSince(LocalDateTime since);

} 



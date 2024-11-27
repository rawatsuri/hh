package com.healthwealth.user.service;



import com.healthwealth.common.exception.DuplicateResourceException;

import com.healthwealth.common.exception.ResourceNotFoundException;

import com.healthwealth.security.util.SecurityUtils;

import com.healthwealth.user.dto.UserProfileUpdateRequest;

import com.healthwealth.user.dto.UserRegistrationRequest;

import com.healthwealth.user.entity.User;

import com.healthwealth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.Cacheable;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



import java.util.Map;

import java.util.Set;



@Service

@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    

    @Transactional

    public User registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");

        }

        

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());

        user.setPhoneNumber(request.getPhoneNumber());

        

        return userRepository.save(user);

    }

    

    @Transactional(readOnly = true)

    public User getCurrentUser() {

        return findById(SecurityUtils.getCurrentUserId());

    }

    

    @Cacheable(value = "users", key = "#id")

    @Transactional(readOnly = true)

    public User findById(Long id) {

        return userRepository.findById(id)

            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    }

    

    @Cacheable(value = "users", key = "#email")

    @Transactional(readOnly = true)

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)

            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

    }

    

    @Transactional

    @CacheEvict(value = "users", key = "#id")

    public User updateProfile(Long id, UserProfileUpdateRequest request) {

        User user = findById(id);

        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());

        user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);

    }

    

    @Transactional

    @CacheEvict(value = "users", key = "#id")

    public void changePassword(Long id, String currentPassword, String newPassword) {

        User user = findById(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {

            throw new BadCredentialsException("Current password is incorrect");

        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

    }

    

    @Transactional

    @CacheEvict(value = "users", key = "#id")

    public void updateHealthConditions(Long id, Set<String> conditions) {

        User user = findById(id);

        user.setHealthConditions(conditions);

        userRepository.save(user);

    }

    

    @Transactional

    @CacheEvict(value = "users", key = "#id")

    public void updatePreferences(Long id, Map<String, String> preferences) {

        User user = findById(id);

        user.getPreferences().putAll(preferences);

        userRepository.save(user);

    }

    

    @Transactional

    @CacheEvict(value = "users", key = "#id")

    public void toggleNotifications(Long id, boolean enabled) {

        User user = findById(id);

        user.setNotificationEnabled(enabled);

        userRepository.save(user);

    }

} 

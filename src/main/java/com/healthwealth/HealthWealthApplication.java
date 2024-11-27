package com.healthwealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.healthwealth")
@EntityScan(basePackages = "com.healthwealth")
@EnableJpaRepositories(basePackages = "com.healthwealth")
public class HealthWealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthWealthApplication.class, args);
    }
} 
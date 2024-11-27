package com.healthwealth.wealth.controller;

import com.healthwealth.security.util.SecurityUtils;
import com.healthwealth.wealth.entity.Budget;
import com.healthwealth.wealth.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget Management", description = "APIs for managing budgets")
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a budget")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        budget.getUser().setId(getCurrentUserId());
        return ResponseEntity.ok(budgetService.createBudget(budget));
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current month budgets")
    public ResponseEntity<List<Budget>> getCurrentBudgets() {
        return ResponseEntity.ok(budgetService.findByUserIdAndPeriod(
            getCurrentUserId(), YearMonth.now()));
    }

    @GetMapping("/{yearMonth}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get budgets for specific month")
    public ResponseEntity<List<Budget>> getBudgetsByMonth(
            @PathVariable YearMonth yearMonth) {
        return ResponseEntity.ok(budgetService.findByUserIdAndPeriod(
            getCurrentUserId(), yearMonth));
    }

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 
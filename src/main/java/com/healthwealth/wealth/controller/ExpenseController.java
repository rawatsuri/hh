package com.healthwealth.wealth.controller;

import com.healthwealth.security.util.SecurityUtils;
import com.healthwealth.wealth.entity.Expense;
import com.healthwealth.wealth.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Tag(name = "Expense Management", description = "APIs for managing expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Log an expense")
    public ResponseEntity<Expense> logExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.logExpense(expense));
    }

    @GetMapping("/summary")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get monthly expense summary")
    public ResponseEntity<Map<String, BigDecimal>> getMonthlyExpenseSummary(
            @RequestParam YearMonth yearMonth) {
        return ResponseEntity.ok(expenseService.getMonthlyExpenseSummary(getCurrentUserId(), yearMonth));
    }

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get expense history")
    public ResponseEntity<Page<Expense>> getExpenseHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(expenseService.getExpenseHistory(
            getCurrentUserId(), startDate, endDate, pageable));
    }

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 
package com.paulo.budgeting.controllers;

import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.domain.MoneyItem;
import com.paulo.budgeting.domain.enums.MoneyItemType;
import com.paulo.budgeting.dto.ExportBudgetRequest;
import com.paulo.budgeting.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/export")
    public ResponseEntity<?> exportBudget(@RequestBody ExportBudgetRequest request, Principal principal) throws IOException {
        String csvAsString = budgetService.exportAsCsv(principal.getName());


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"")
                .body(csvAsString);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() throws IOException {
        List<MoneyItem> expenseList = new ArrayList<>();

        expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value(BigDecimal.ONE).title("Harriet").build());
        expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value(BigDecimal.TEN).title("Namdy").build());

        expenseList.add(MoneyItem.builder().type(MoneyItemType.INCOME).value(BigDecimal.valueOf(35)).title("WAGE 1").build());
        expenseList.add(MoneyItem.builder().type(MoneyItemType.INCOME).value(BigDecimal.TEN).title("WAGE 2").build());

        Budget budget = Budget.builder().moneyItems(expenseList).build();

        String csvAsString = budgetService.exportAsCsv(budget);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"")
                .body(csvAsString);
    }
}

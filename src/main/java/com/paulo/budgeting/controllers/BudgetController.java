package com.paulo.budgeting.controllers;

import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.domain.MoneyItem;
import com.paulo.budgeting.domain.enums.MoneyItemType;
import com.paulo.budgeting.dto.ExportBudgetRequest;
import com.paulo.budgeting.dto.RemoveBudgetRequest;
import com.paulo.budgeting.dto.SaveBudgetRequest;
import com.paulo.budgeting.dto.SaveBudgetResponse;
import com.paulo.budgeting.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/export")
    public ResponseEntity<?> exportBudget(@RequestBody ExportBudgetRequest request, Principal principal) throws IOException {
        String csvAsString = budgetService.exportAsCsv(request);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"")
                .body(csvAsString);
    }

    @PostMapping("/save")
    public ResponseEntity<SaveBudgetResponse> save(@RequestBody SaveBudgetRequest request, Principal principal) {
        log.info("{}\n{}", principal.getName(), request);
        Budget budget = budgetService.save(request, principal.getName());
        SaveBudgetResponse saveBudgetResponse = new SaveBudgetResponse(budget.getId());

        return ResponseEntity.ok().body(saveBudgetResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody RemoveBudgetRequest request, Principal principal) {
        log.info("{}\n{}", principal.getName(), request);
        budgetService.removeBudget(request, principal.getName());

        return ResponseEntity.ok().build();
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

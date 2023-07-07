package com.paulo.budgeting.service;

import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.dto.CreateBudgetRequest;
import com.paulo.budgeting.exporters.BudgetToCsvExporter;
import com.paulo.budgeting.repo.BudgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepo repo;

    private final BudgetToCsvExporter budgetToCsvExporter;

    public Budget createBudget(CreateBudgetRequest request, String email) {
        Budget budget = new Budget();
        budget.setMoneyItems(request.getMoneyItems());
        budget.setUserEmail(email);

        return repo.save(budget);
    }

    public Optional<Budget> findBudgetByEmail(String email) {
        return repo.findBudgetByUserEmail(email);
    }

    public Boolean doesBudgetExistByEmail(String email) {
        return repo.existsByUserEmail(email);
    }

    public String exportAsCsv(String email) {
        return findBudgetByEmail(email)
                .map(budget -> {
                    try {
                        return budgetToCsvExporter.export(budget);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Was not able to export budget as csv"));
    }

    public String exportAsCsv(Budget budget) {
        try {
            return budgetToCsvExporter.export(budget);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.paulo.budgeting;

import com.paulo.budgeting.dto.BudgetDto;
import com.paulo.budgeting.dto.MoneyItemDto;
import com.paulo.budgeting.exporters.BudgetToCsvExporter;
import com.paulo.budgeting.repo.BudgetRepo;
import com.paulo.budgeting.repo.MoneyItemRepo;
import com.paulo.budgeting.service.BudgetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;
    @Mock
    private BudgetRepo repo;
    @Mock
    private BudgetToCsvExporter budgetToCsvExporter;

    @Test
    public void validateBudget_throws_1() {
        String validTitle = "expense";
        BigDecimal validExpense = BigDecimal.valueOf(10000);
        BigDecimal validIncome = BigDecimal.valueOf(51000);
        BudgetDto invalidExpense = getBudgetDto(validExpense, validIncome, validTitle);

        assertThrows(RuntimeException.class, () -> budgetService.validateBudget(invalidExpense));
    }

    @Test
    public void validateBudget_throws_2() {
        String validTitle = "income";
        BigDecimal validExpense = BigDecimal.valueOf(10000);
        BigDecimal validIncome = BigDecimal.valueOf(51000);

        BudgetDto invalidIncome = getBudgetDto(validExpense, validIncome, validTitle);

        assertThrows(RuntimeException.class, () -> budgetService.validateBudget(invalidIncome));
    }

    @Test
    public void validateBudget_throws_3() {
        String validTitle = "WADWDAWDAWDAWDAWDEAWEAWEAWEAWEAWEAW";
        BigDecimal validExpense = BigDecimal.valueOf(1000);
        BigDecimal validIncome = BigDecimal.valueOf(2000);

        BudgetDto invalidIncome = getBudgetDto(validExpense, validIncome, validTitle);

        assertThrows(RuntimeException.class, () -> budgetService.validateBudget(invalidIncome));
    }

    @Test
    public void validateBudget_does_not_throw() {
        String validTitle = "Dog";
        BigDecimal validExpense = BigDecimal.valueOf(1000);
        BigDecimal validIncome = BigDecimal.valueOf(2000);

        BudgetDto invalidIncome = getBudgetDto(validExpense, validIncome, validTitle);

        assertDoesNotThrow(() -> budgetService.validateBudget(invalidIncome));
    }

    private BudgetDto getBudgetDto(BigDecimal expenseAmount, BigDecimal incomeAmount, String title) {
        MoneyItemDto expense = MoneyItemDto.builder().value(expenseAmount).title(title).build();
        MoneyItemDto income = MoneyItemDto.builder().value(incomeAmount).title(title).build();

        return BudgetDto.builder().expenses(List.of(expense)).incomes(List.of(income)).build();
    }
}

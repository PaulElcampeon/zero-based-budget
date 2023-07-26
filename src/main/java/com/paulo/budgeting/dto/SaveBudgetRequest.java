package com.paulo.budgeting.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveBudgetRequest {
    private String budgetName;
    private List<MoneyItemDto> expenses;
    private List<MoneyItemDto> incomes;
}

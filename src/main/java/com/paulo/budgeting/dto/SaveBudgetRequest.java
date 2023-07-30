package com.paulo.budgeting.dto;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class SaveBudgetRequest {
    private final Optional<Long> id;
    private final String title;
    private final List<MoneyItemDto> expenses;
    private final List<MoneyItemDto> incomes;
}

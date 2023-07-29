package com.paulo.budgeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private String title;
    private List<MoneyItemDto> expenses;
    private List<MoneyItemDto> incomes;
}

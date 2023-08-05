package com.paulo.budgeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportBudgetRequest {
    private String title;
    private List<MoneyItemDto> expenses;
    private List<MoneyItemDto> incomes;
}

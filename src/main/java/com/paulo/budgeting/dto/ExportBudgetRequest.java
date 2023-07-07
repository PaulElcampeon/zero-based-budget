package com.paulo.budgeting.dto;

import com.paulo.budgeting.domain.MoneyItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportBudgetRequest {
    private BigDecimal income;
    private BigDecimal remaining;
    private List<MoneyItem> moneyItems;
}

package com.paulo.budgeting.dto;

import com.paulo.budgeting.domain.MoneyItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateBudgetRequest {

    private String title;
    private BigDecimal income;
    private BigDecimal remaining;
    private List<MoneyItem> moneyItems;
}

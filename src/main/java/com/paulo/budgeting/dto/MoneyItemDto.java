package com.paulo.budgeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyItemDto {
    private String position;
    private String title;
    private BigDecimal value;
}

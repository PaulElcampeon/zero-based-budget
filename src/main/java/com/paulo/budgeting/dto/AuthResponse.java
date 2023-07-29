package com.paulo.budgeting.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private final String token;

    private final List<BudgetDto> budget;

}

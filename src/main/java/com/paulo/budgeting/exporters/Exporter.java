package com.paulo.budgeting.exporters;

import com.paulo.budgeting.dto.BudgetDto;

import java.io.IOException;

public interface Exporter<T> {

    T export(BudgetDto budgetDto) throws IOException;
}

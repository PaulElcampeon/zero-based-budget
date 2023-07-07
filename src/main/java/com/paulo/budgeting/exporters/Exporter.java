package com.paulo.budgeting.exporters;

import com.paulo.budgeting.domain.Budget;

import java.io.IOException;

public interface Exporter<T> {

     T export(Budget budget) throws IOException;
}

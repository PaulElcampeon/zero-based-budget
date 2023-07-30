package com.paulo.budgeting.repo;

import com.paulo.budgeting.domain.MoneyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyItemRepo extends JpaRepository<MoneyItem, Long> {

    void deleteAllByBudgetId(Long budgetId);
}

package com.paulo.budgeting.repo;

import com.paulo.budgeting.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    Optional<Budget> findBudgetByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

}

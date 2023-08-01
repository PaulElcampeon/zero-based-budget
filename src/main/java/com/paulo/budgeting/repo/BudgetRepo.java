package com.paulo.budgeting.repo;

import com.paulo.budgeting.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    void deleteByIdAndUserEmail(Long id, String userEmail);

}

package com.paulo.budgeting.service;

import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.domain.MoneyItem;
import com.paulo.budgeting.domain.enums.MoneyItemType;
import com.paulo.budgeting.dto.BudgetDto;
import com.paulo.budgeting.dto.CreateBudgetRequest;
import com.paulo.budgeting.dto.ExportBudgetRequest;
import com.paulo.budgeting.dto.RemoveBudgetRequest;
import com.paulo.budgeting.dto.SaveBudgetRequest;
import com.paulo.budgeting.exporters.BudgetToCsvExporter;
import com.paulo.budgeting.repo.BudgetRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepo repo;
    private final BudgetToCsvExporter budgetToCsvExporter;

    private static final BigDecimal INCOME_MAX = BigDecimal.valueOf(50000);
    private static final BigDecimal EXPENSE_MAX = BigDecimal.valueOf(10000);
    private static final int TITLE_MAX_LENGTH = 20;


    public Budget createBudget(CreateBudgetRequest request, String email) {
        Budget budget = new Budget();
        budget.setMoneyItems(request.getMoneyItems());
        budget.setUserEmail(email);

        return repo.save(budget);
    }

    public List<Budget> findBudgetsByEmail(String email) {
        return repo.findAllByUserEmail(email);
    }

    public Optional<Budget> findById(Long id) {
        return repo.findById(id);
    }

    public Boolean doesBudgetExistByEmail(String email) {
        return repo.existsByUserEmail(email);
    }

    public String exportAsCsv(ExportBudgetRequest request) {
//        Budget budget = Budget.builder()
//                .title(request.getBudgetName())
//                .expenses(request
//                        .getExpenses()
//                        .stream()
//                        .map(moneyItemDto -> MoneyItem.builder().position(moneyItemDto.getPosition()).value(moneyItemDto.getValue()).title(moneyItemDto.getTitle()).build()).collect(Collectors.toList()))
//                .incomes(request
//                        .getIncomes()
//                        .stream()
//                        .map(moneyItemDto -> MoneyItem.builder().position(moneyItemDto.getPosition()).value(moneyItemDto.getValue()).title(moneyItemDto.getTitle()).build()).collect(Collectors.toList()))
//                .build();

        BudgetDto budgetDto = BudgetDto.builder().incomes(request.getIncomes()).expenses(request.getExpenses()).title(request.getBudgetName()).build();

        try {
            return budgetToCsvExporter.export(budgetDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String exportAsCsv(Budget budget) {
        try {
            return budgetToCsvExporter.export(budget.mapToDto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Budget save(SaveBudgetRequest request, String email) {
        validateBudget(BudgetDto.builder().title(request.getTitle()).incomes(request.getIncomes()).expenses(request.getExpenses()).build());

        Budget budget;
        if (request.getId().filter(id -> id != 0).isPresent()) {
            Long id = request.getId().get();
            budget = repo.findById(id).orElseThrow(() -> new RuntimeException("Could not find budget with id " + request.getId().get()));
            //Remove All MoneyItems associated with the budget and insert them all again as some may be new or update or even removed
            budget.getMoneyItems().clear();
        } else {
            budget = new Budget();
        }

        budget.setTitle(request.getTitle());
        budget.setUserEmail(email);

        List<MoneyItem> expense = request.getExpenses()
                .stream()
                .map(moneyItemDto ->
                        MoneyItem.builder()
                                .type(MoneyItemType.EXPENSE)
                                .title(moneyItemDto.getTitle())
                                .value(moneyItemDto.getValue())
                                .position(moneyItemDto.getPosition())
                                .build()
                )
                .toList();

        List<MoneyItem> incomes = request.getIncomes()
                .stream()
                .map(moneyItemDto ->
                        MoneyItem.builder()
                                .type(MoneyItemType.INCOME)
                                .title(moneyItemDto.getTitle())
                                .value(moneyItemDto.getValue())
                                .position(moneyItemDto.getPosition())
                                .build()
                )
                .toList();

        List<MoneyItem> combinedMoneyList = Stream.of(expense, incomes).flatMap(Collection::stream).toList();

        //It is important to use addAll rather than setMoneyItems(combinedMoneyList) because you want the parent entity
        // to still reference the old child collection so it can update the child elements e.g delete them if they have
        // been removed from the collection
        budget.getMoneyItems().addAll(combinedMoneyList);
        budget.getMoneyItems().forEach(moneyItem -> moneyItem.setBudget(budget));

        return repo.save(budget);
    }

    @Transactional
    public void removeBudget(RemoveBudgetRequest request, String userEmail) {
        repo.deleteByIdAndUserEmail(request.getBudgetId(), userEmail);
    }

    public void validateBudget(BudgetDto budgetDto) {
        budgetDto.getIncomes().forEach(moneyItemDto -> {
            if (moneyItemDto.getValue().compareTo(INCOME_MAX) >= 1) {
                throw new RuntimeException("Income exceeded max value");
            }
            if (moneyItemDto.getTitle().length() >= TITLE_MAX_LENGTH) {
                throw new RuntimeException("Title exceeded max length");
            }
        });

        budgetDto.getExpenses().forEach(moneyItemDto -> {
            if (moneyItemDto.getValue().compareTo(EXPENSE_MAX) >= 1) {
                throw new RuntimeException("Expense exceeded max value");
            }
            if (moneyItemDto.getTitle().length() >= TITLE_MAX_LENGTH) {
                throw new RuntimeException("Title exceeded max length");
            }
        });
    }
}

package com.paulo.budgeting.domain;

import com.paulo.budgeting.domain.enums.MoneyItemType;
import com.paulo.budgeting.dto.BudgetDto;
import com.paulo.budgeting.dto.MoneyItemDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "budgets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userEmail", nullable = false)
    private String userEmail;
    @Column(name = "title", nullable = false)
    private String title;
    @Transient
    @Builder.Default
    private List<MoneyItem> incomes = new ArrayList<>();
    @Transient
    @Builder.Default
    private List<MoneyItem> expenses = new ArrayList<>();
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MoneyItem> moneyItems = new ArrayList<>();
    @Column(nullable = false)
    private Instant createAt;
    @Column(nullable = false)
    private Instant updateAt;

    public List<MoneyItem> getIncomes() {
        return incomes.isEmpty() ? moneyItems.stream().filter((moneyItem -> moneyItem.getType() == MoneyItemType.INCOME)).collect(Collectors.toList())
                :
                incomes;
    }

    public List<MoneyItem> getExpenses() {
        return expenses.isEmpty() ? moneyItems.stream().filter((moneyItem -> moneyItem.getType() == MoneyItemType.EXPENSE)).collect(Collectors.toList())
                :
                expenses;
    }

    @PrePersist
    public void prePersist() {
        createAt = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        updateAt = createAt;
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = Instant.now().truncatedTo(ChronoUnit.MINUTES);
    }

    @PostLoad
    public void postLoad() {
        incomes = moneyItems
                .stream()
                .filter((moneyItem -> moneyItem.getType() == MoneyItemType.INCOME))
                .collect(Collectors.toList());

        expenses = moneyItems
                .stream()
                .filter((moneyItem -> moneyItem.getType() == MoneyItemType.EXPENSE))
                .collect(Collectors.toList());
    }

    public BudgetDto mapToDto() {
        return BudgetDto
                .builder()
                .id(id)
                .expenses(getExpenses().stream().map(MoneyItem::mapToDto).sorted(Comparator.comparingInt(MoneyItemDto::getPosition)).collect(Collectors.toList()))
                .incomes(getIncomes().stream().map(MoneyItem::mapToDto).sorted(Comparator.comparingInt(MoneyItemDto::getPosition)).collect(Collectors.toList()))
                .title(title)
                .build();
    }
}

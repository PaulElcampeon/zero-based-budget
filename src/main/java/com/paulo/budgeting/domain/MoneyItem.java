package com.paulo.budgeting.domain;

import com.paulo.budgeting.domain.enums.MoneyItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "money_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "value", nullable = false)
    private BigDecimal value;
    @Column(name = "type", nullable = false)
    private MoneyItemType type;
    @ManyToOne
    @JoinColumn(name ="budget_id", nullable = false)
    private Budget budget;
}

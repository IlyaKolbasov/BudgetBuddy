package by.kolbasov.BudgetBuddy.entity;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal sum;
    private String currency;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private boolean limitExceeded;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="limit_id")
    private Limit limit;



}

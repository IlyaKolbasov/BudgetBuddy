package by.kolbasov.BudgetBuddy.entity;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.validator.ValidAccountNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ValidAccountNumber
    private Long accountFrom;
    @ValidAccountNumber
    private Long accountTo;
    @Min(value = 0, message = "Amount must be zero or positive")
    private BigDecimal sum;
    @Size(min = 3, max = 3, message = "Currency short name must be exactly 3 characters long")
    private String currencyShortName;
    private ZonedDateTime dateTime;
    private double rate;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private boolean limitExceeded;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="limit_id")
    private Limit limit;

}

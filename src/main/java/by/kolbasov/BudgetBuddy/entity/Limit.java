
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
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="limit_transaction")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ValidAccountNumber
    private long accountFrom;
    @Min(value = 0, message = "Amount must be zero or positive")
    private BigDecimal limitSum;
    private BigDecimal remainingLimit;
    private ZonedDateTime limitDateTime;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    @Size(min = 3, max = 3, message = "Currency short name must be exactly 3 characters long")
    private String limitCurrency;
    @OneToMany(mappedBy = "limit")
    private List<Transaction> transactions;

}

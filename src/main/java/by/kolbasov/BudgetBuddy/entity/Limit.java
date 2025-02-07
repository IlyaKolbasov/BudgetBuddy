
package by.kolbasov.BudgetBuddy.entity;


import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.*;
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
    private long accountFrom;
    private BigDecimal limitSum;
    private BigDecimal remainingLimit;
    private ZonedDateTime limitDateTime;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private String limitCurrency;
    @OneToMany(mappedBy = "limit")
    private List<Transaction> transactions;

}

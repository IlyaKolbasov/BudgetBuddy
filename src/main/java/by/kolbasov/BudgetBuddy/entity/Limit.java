
package by.kolbasov.BudgetBuddy.entity;


import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private Long userId;
    private BigDecimal sumUSD;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    @OneToMany(mappedBy = "limit")
    private List<Transaction> transactions;

}

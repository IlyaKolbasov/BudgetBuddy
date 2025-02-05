package by.kolbasov.BudgetBuddy.DTO;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private Long userId;
    private BigDecimal sum;
    private String currency;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

}

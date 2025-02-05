package by.kolbasov.BudgetBuddy.DTO;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitRequest {
    private Long userId;
    private BigDecimal sumUSD;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
}

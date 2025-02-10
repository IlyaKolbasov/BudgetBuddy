package by.kolbasov.BudgetBuddy.DTO;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.validator.ValidAccountNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
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
    @ValidAccountNumber
    private Long accountFrom;
    @Min(value = 0, message = "Amount must be zero or positive")
    private BigDecimal sumUsd;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
}

package by.kolbasov.BudgetBuddy.DTO;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.validator.ValidAccountNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @ValidAccountNumber
    private Long accountFrom;
    @ValidAccountNumber
    private Long accountTo;
    @Min(value = 0, message = "Amount must be zero or positive")
    private BigDecimal sum;
    @Size(min = 3, max = 3, message = "Currency short name must be exactly 3 characters long")
    private String currencyShortName;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

}

package by.kolbasov.BudgetBuddy.DTO;

import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceededTransactionDTO {
    private Long accountFrom;
    private Long accountTo;
    private String currencyShortName;
    private BigDecimal sum;
    private ZonedDateTime dateTime;
    private ExpenseCategory expenseCategory;
    private BigDecimal limitSum;
    private ZonedDateTime limitDateTime;
    private String limitCurrency;

}

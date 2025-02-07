package by.kolbasov.BudgetBuddy.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class ConversionResult {
    private final BigDecimal convertedAmount;
    private final double rate;
}

package by.kolbasov.BudgetBuddy.service;


import by.kolbasov.BudgetBuddy.entity.ExchangeRate;
import by.kolbasov.BudgetBuddy.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyConverterService {
    private final ExchangeRateRepository exchangeRateRepository;

    public BigDecimal convertCurrency (BigDecimal amount, String currency) {

        ExchangeRate exchangeRate = exchangeRateRepository.findBySymbol(currency+"/USD")
                .orElseThrow(() -> new IllegalArgumentException("Exchange rate not found for " + currency + "/USD"));

        return amount.multiply(new BigDecimal(exchangeRate.getRate())).setScale(2, BigDecimal.ROUND_HALF_UP);

    }
}

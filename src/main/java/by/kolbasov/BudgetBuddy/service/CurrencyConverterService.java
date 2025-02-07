package by.kolbasov.BudgetBuddy.service;


import by.kolbasov.BudgetBuddy.DTO.ConversionResult;
import by.kolbasov.BudgetBuddy.entity.ExchangeRate;
import by.kolbasov.BudgetBuddy.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConverterService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;

    public ConversionResult convertCurrency(BigDecimal amount, String currency) {

        if (currency.contains("KZT") || currency.contains("RUB")) {
            ExchangeRate exchangeRate = exchangeRateRepository.findBySymbol(currency + "/USD")
                    .orElseThrow(() -> new IllegalArgumentException("Exchange rate not found for " + currency + "/USD"));

            return ConversionResult.builder()
                    .convertedAmount(amount.multiply(BigDecimal.valueOf(exchangeRate.getRate()))
                            .setScale(2, RoundingMode.HALF_UP))
                    .rate(exchangeRate.getRate())
                    .build();
        } else {
            String urlString = "https://api.twelvedata.com/currency_conversion?symbol=" + currency + "/USD" + "&apikey=2f69b83a981440d2a7fecafc251fa120";
            ExchangeRate exchangeRate = restTemplate.getForObject(urlString, ExchangeRate.class);
            return  ConversionResult.builder()
                    .convertedAmount(amount.multiply(BigDecimal.valueOf(exchangeRate.getRate()))
                            .setScale(2, RoundingMode.HALF_UP))
                    .rate(exchangeRate.getRate())
                    .build();
        }

    }
}

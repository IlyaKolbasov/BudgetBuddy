package by.kolbasov.BudgetBuddy.service;


import by.kolbasov.BudgetBuddy.entity.ExchangeRate;

import by.kolbasov.BudgetBuddy.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;

    @Value("${twelvedata.api.url}")
    private String apiUrl;
    @Value("${twelvedata.api.key}")
    private String apiKey;

    @Scheduled(cron = "0 00 11 * * ?")
    @Scheduled(cron = "0 50 23 * * ?")// закрытие торгов
    private void convertCurrency(){
        updateExchangeRate( "RUB/USD");
        updateExchangeRate("KZT/USD");
    }
    private void updateExchangeRate(String symbol){
        String urlString = String.format("%s?symbol=%s&apikey=%s", apiUrl, symbol, apiKey);
            ExchangeRate exchangeRate = restTemplate.getForObject(urlString, ExchangeRate.class);
            exchangeRateRepository.findBySymbol(symbol).ifPresentOrElse(
                        existingRate -> {
                            existingRate.setSymbol(exchangeRate.getSymbol());
                            existingRate.setRate(exchangeRate.getRate());
                            existingRate.setDate(ZonedDateTime.now());
                            exchangeRateRepository.save(existingRate);
                        },
                         ()->
                         {
                             exchangeRate.setDate(ZonedDateTime.now());
                             exchangeRateRepository.save(exchangeRate);
                         });
            }

        }


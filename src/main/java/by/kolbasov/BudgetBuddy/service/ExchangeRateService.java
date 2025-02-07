package by.kolbasov.BudgetBuddy.service;


import by.kolbasov.BudgetBuddy.entity.ExchangeRate;
import by.kolbasov.BudgetBuddy.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 00 11 * * ?")
    @Scheduled(cron = "0 50 23 * * ?")// закрытие торгов
    private void convertCurrency(){
        updateExchangeRate("RUB/USD");
        updateExchangeRate("KZT/USD");
    }
    private void updateExchangeRate(String symbol){
        String urlString = "https://api.twelvedata.com/currency_conversion?symbol="+symbol+"&apikey=2f69b83a981440d2a7fecafc251fa120";
            ExchangeRate exchangeRate = restTemplate.getForObject(urlString, ExchangeRate.class);
            exchangeRateRepository.findBySymbol(symbol).ifPresentOrElse(
                        existingRate -> {
                            existingRate.setSymbol(exchangeRate.getSymbol());
                            existingRate.setRate(exchangeRate.getRate());
                            existingRate.setDate(LocalDateTime.now());
                            exchangeRateRepository.save(existingRate);
                        },
                         ()->
                         {
                             exchangeRate.setDate(LocalDateTime.now());
                             exchangeRateRepository.save(exchangeRate);
                         });
            }

        }


package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.DTO.ConversionResult;
import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.repository.ExchangeRateRepository;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final CurrencyConverterService currencyConverterService;


    public void saveTransaction(TransactionRequest transactionRequest) {
        Limit limit = limitRepository.findTopByAccountFromAndExpenseCategoryOrderByLimitDateTimeDesc(transactionRequest.getAccountFrom(), transactionRequest.getExpenseCategory())
                .orElse(Limit.builder()
                        .accountFrom(transactionRequest.getAccountFrom())
                        .expenseCategory(transactionRequest.getExpenseCategory())
                        .limitDateTime(ZonedDateTime.now())
                        .limitCurrency("USD")
                        .limitSum(BigDecimal.valueOf(1000))
                        .remainingLimit(BigDecimal.valueOf(1000))
                        .build());

        ConversionResult conversionResult = currencyConverterService.convertCurrency(transactionRequest.getSum(), transactionRequest.getCurrencyShortName());

        limit.setRemainingLimit(limit.getRemainingLimit().subtract(conversionResult.getConvertedAmount()));

        if (limit.getRemainingLimit().compareTo(BigDecimal.valueOf(0)) >= 0) {
            transactionRepository.save(Transaction.builder()
                    .accountFrom(transactionRequest.getAccountFrom())
                    .accountTo(transactionRequest.getAccountTo())
                    .sum(transactionRequest.getSum())
                    .currencyShortName(transactionRequest.getCurrencyShortName())
                    .dateTime(ZonedDateTime.now())
                    .rate(conversionResult.getRate())
                    .expenseCategory(transactionRequest.getExpenseCategory())
                    .limit(limit)
                    .limitExceeded(false)
                    .build());
        } else {
            transactionRepository.save(Transaction.builder()
                    .accountFrom(transactionRequest.getAccountFrom())
                    .accountTo(transactionRequest.getAccountTo())
                    .sum(transactionRequest.getSum())
                    .currencyShortName(transactionRequest.getCurrencyShortName())
                    .dateTime(ZonedDateTime.now())
                    .rate(conversionResult.getRate())
                    .expenseCategory(transactionRequest.getExpenseCategory())
                    .limit(limit)
                    .limitExceeded(true)
                    .build());
        }
    }
}

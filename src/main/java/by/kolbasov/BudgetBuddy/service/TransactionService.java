package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final CurrencyConverterService currencyConverterService;

    public void saveTransaction(TransactionRequest transactionRequest) {
        Limit limit = limitRepository.findTopByAccountFromAndExpenseCategoryOrderByDateTimeDesc(transactionRequest.getAccountFrom(),transactionRequest.getExpenseCategory())
                .orElse(Limit.builder()
                        .accountFrom(transactionRequest.getAccountFrom())
                        .expenseCategory(transactionRequest.getExpenseCategory())
                        .dateTime(ZonedDateTime.now())
                        .sumUsd(BigDecimal.valueOf(1000))
                        .remainingLimit(BigDecimal.valueOf(1000))
                        .build());

        limit.setRemainingLimit(limit.getRemainingLimit().subtract(currencyConverterService.convertCurrency(transactionRequest.getSum(), transactionRequest.getCurrencyShortName())));

        if(limit.getRemainingLimit().compareTo(BigDecimal.valueOf(0)) >= 0){
            transactionRepository.save( Transaction.builder()
                    .accountFrom(transactionRequest.getAccountFrom())
                    .accountTo(transactionRequest.getAccountTo())
                    .sum(transactionRequest.getSum())
                    .currencyShortName(transactionRequest.getCurrencyShortName())
                    .dateTime(ZonedDateTime.now())
                    .expenseCategory(transactionRequest.getExpenseCategory())
                    .limit(limit)
                    .limitExceeded(false)
                    .build());

        }else{
            transactionRepository.save(Transaction.builder()
                    .accountFrom(transactionRequest.getAccountFrom())
                    .accountTo(transactionRequest.getAccountTo())
                    .sum(transactionRequest.getSum())
                    .currencyShortName(transactionRequest.getCurrencyShortName())
                    .dateTime(ZonedDateTime.now())
                    .expenseCategory(transactionRequest.getExpenseCategory())
                    .limit(limit)
                    .limitExceeded(true)
                    .build());
        }




    }
}

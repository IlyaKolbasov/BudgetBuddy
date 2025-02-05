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

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final CurrencyConverterService currencyConverterService;

    public void saveTransaction(TransactionRequest transactionRequest) {
        Limit limit = limitRepository.findByUserIdAndExpenseCategory(transactionRequest.getUserId(),transactionRequest.getExpenseCategory())
                .orElse(Limit.builder()
                        .userId(transactionRequest.getUserId())
                        .expenseCategory(transactionRequest.getExpenseCategory())
                        .date(LocalDateTime.now())
                        .sumUSD(BigDecimal.valueOf(1000))
                        .build());




        limit.setSumUSD(limit.getSumUSD().subtract(currencyConverterService.convertCurrency(transactionRequest.getSum(), transactionRequest.getCurrency())));

        if(limit.getSumUSD().compareTo(BigDecimal.valueOf(0)) >= 0){
            Transaction transaction = Transaction.builder()
                    .userId(transactionRequest.getUserId())
                    .sum(transactionRequest.getSum())
                    .currency(transactionRequest.getCurrency())
                    .date(LocalDateTime.now())
                    .expenseCategory(transactionRequest.getExpenseCategory())
                    .limit(limit)
                    .limitExceeded(false)
                    .build();
            transactionRepository.save(transaction);
        }




    }
}

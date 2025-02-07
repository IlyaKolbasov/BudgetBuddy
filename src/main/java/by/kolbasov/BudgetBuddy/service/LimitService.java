package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.DTO.LimitRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyConverterService currencyConverterService;

    public void saveLimit(LimitRequest limitRequest) {
        if (limitRepository.findTopByAccountFromAndExpenseCategoryOrderByDateTimeDesc(limitRequest.getAccountFrom(), limitRequest.getExpenseCategory()).isPresent()) {

            limitRepository.save(Limit.builder()
                    .accountFrom(limitRequest.getAccountFrom())
                    .sumUsd(limitRequest.getSumUsd())
                    .remainingLimit(limitRequest.getSumUsd()
                            .subtract(sumAmountsByCurrency(limitRequest.getAccountFrom(), limitRequest.getExpenseCategory())))
                    .expenseCategory(limitRequest.getExpenseCategory())
                    .dateTime(ZonedDateTime.now())
                    .build());
        }else {
            limitRepository.save(Limit.builder()
                    .accountFrom(limitRequest.getAccountFrom())
                    .sumUsd(limitRequest.getSumUsd())
                    .remainingLimit(limitRequest.getSumUsd())
                    .expenseCategory(limitRequest.getExpenseCategory())
                    .dateTime(ZonedDateTime.now())
                    .build());
        }
    }


    private BigDecimal sumAmountsByCurrency(Long accountFrom, ExpenseCategory expenseCategory) {
        List<Transaction> transactions = transactionRepository
                .findAllByAccountFromAndExpenseCategory(accountFrom, expenseCategory);
        return transactions.stream()
                .map(transaction -> transaction.getSum()
                        .multiply(BigDecimal.valueOf(transaction.getRate()))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
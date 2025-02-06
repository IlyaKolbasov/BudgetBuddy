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
                            .subtract(sumAmountsByCurrency(limitRequest.getAccountFrom(), "RUB", limitRequest.getExpenseCategory()))
                            .subtract(sumAmountsByCurrency(limitRequest.getAccountFrom(), "KZT", limitRequest.getExpenseCategory())))
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


    private BigDecimal sumAmountsByCurrency(Long accountFrom, String currencyShortName, ExpenseCategory expenseCategory) {
        List<Transaction> transactions = transactionRepository
                .findAllByAccountFromAndCurrencyShortNameAndExpenseCategory(accountFrom, currencyShortName, expenseCategory);
        return transactions.stream()
                .map(transaction -> currencyConverterService.convertCurrency(transaction.getSum(), transaction.getCurrencyShortName()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
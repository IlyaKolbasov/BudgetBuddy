package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.DTO.LimitRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;

    public void saveLimit(LimitRequest limitRequest) {
        limitRepository.save(Limit.builder()
                .accountFrom(limitRequest.getAccountFrom())
                .sumUsd(limitRequest.getSumUsd())
                .remainingLimit(limitRequest.getSumUsd())
                .expenseCategory(limitRequest.getExpenseCategory())
                .dateTime(ZonedDateTime.now())
                .build());

    }
}
package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.DTO.LimitRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;

    public void saveLimit(LimitRequest limitRequest) {
        Limit limit = Limit.builder()
                .userId(limitRequest.getUserId())
                .sumUSD(limitRequest.getSumUSD())
                .expenseCategory(limitRequest.getExpenseCategory())
                .date(LocalDateTime.now())
                .build();
        limitRepository.save(limit);
    }
}
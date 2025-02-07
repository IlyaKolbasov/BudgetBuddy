package by.kolbasov.BudgetBuddy.service;

import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class UpdateLimitsService {
    private final LimitRepository limitRepository;
     @Scheduled(cron = "0 0 0 1 * ?") // обновление лимитов 1-го числа нового месяца
    public void updateLimits() {
        List<Limit> limits = limitRepository.findAll();
        for (Limit limit : limits) {
            limit.setRemainingLimit(limit.getLimitSum());
            limit.setLimitDateTime(ZonedDateTime.now());
            limitRepository.save(limit);
        }
    }

}

package by.kolbasov.BudgetBuddy.repository;

import by.kolbasov.BudgetBuddy.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findBySymbol(String symbol);

}

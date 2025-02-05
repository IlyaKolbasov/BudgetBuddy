package by.kolbasov.BudgetBuddy.repository;

import by.kolbasov.BudgetBuddy.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

package by.kolbasov.BudgetBuddy.repository;

import by.kolbasov.BudgetBuddy.DTO.ExceededTransactionDTO;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountFromAndExpenseCategory(Long accountFrom,  ExpenseCategory expenseCategory);

    @Query("SELECT new by.kolbasov.BudgetBuddy.DTO.ExceededTransactionDTO(t.accountFrom, t.accountTo, t.currencyShortName, t.sum, " +
            "t.dateTime, t.expenseCategory, l.limitSum, l.limitDateTime, l.limitCurrency) " +
            "FROM Transaction t JOIN t.limit l WHERE t.limitExceeded = true")
    List<ExceededTransactionDTO> findExceededTransactionDetails();

}

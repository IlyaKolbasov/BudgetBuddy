package by.kolbasov.BudgetBuddy.repository;

import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

    Optional<Limit> findTopByAccountFromAndExpenseCategoryOrderByDateTimeDesc(Long account_from, ExpenseCategory expense_category);
}

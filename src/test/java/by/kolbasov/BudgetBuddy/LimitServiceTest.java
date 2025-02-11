package by.kolbasov.BudgetBuddy;

import by.kolbasov.BudgetBuddy.DTO.LimitRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import by.kolbasov.BudgetBuddy.service.LimitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LimitServiceTest {
    @Mock
    private LimitRepository limitRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private LimitService limitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLimit_whenLimitExists() {

        LimitRequest limitRequest = new LimitRequest(9999999999L, BigDecimal.valueOf(2000), ExpenseCategory.product);


        when(limitRepository.findTopByAccountFromAndExpenseCategoryOrderByLimitDateTimeDesc(any(), any()))
                .thenReturn(Optional.of(new Limit()));


        when(transactionRepository.findAllByAccountFromAndExpenseCategory(any(), any()))
                .thenReturn(List.of(Transaction.builder()
                        .accountFrom(9999999999L)
                        .accountTo(1234567890L)
                        .sum(BigDecimal.valueOf(1200))
                        .currencyShortName("RUB")
                        .dateTime(ZonedDateTime.now())
                        .rate(0.01030944)
                        .expenseCategory(ExpenseCategory.product)
                        .limitExceeded(false)
                        .build(),
                        Transaction.builder()
                                .accountFrom(9999999999L)
                                .accountTo(1234567890L)
                                .sum(BigDecimal.valueOf(6800))
                                .currencyShortName("KZT")
                                .dateTime(ZonedDateTime.now())
                                .rate(0.001959702)
                                .expenseCategory(ExpenseCategory.product)
                                .limitExceeded(false)
                                .build()));


        limitService.saveLimit(limitRequest);


        ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
        verify(limitRepository, times(1)).save(limitCaptor.capture());

        Limit savedLimit = limitCaptor.getValue();
        assertNotNull(savedLimit);
        assertEquals(BigDecimal.valueOf(2000), savedLimit.getLimitSum());

        BigDecimal result = BigDecimal.valueOf(2000)
                .subtract(
                        BigDecimal.valueOf(1200)
                                .multiply(BigDecimal.valueOf(0.01030944))
                                .setScale(2, RoundingMode.HALF_UP)
                                .add(
                                        BigDecimal.valueOf(6800)
                                                .multiply(BigDecimal.valueOf(0.001959702))
                                                .setScale(2, RoundingMode.HALF_UP)
                                )
                )
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(result, savedLimit.getRemainingLimit());
    }
}


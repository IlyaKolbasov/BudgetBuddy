package by.kolbasov.BudgetBuddy;

import by.kolbasov.BudgetBuddy.DTO.ConversionResult;
import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Limit;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.model.ExpenseCategory;
import by.kolbasov.BudgetBuddy.repository.LimitRepository;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import by.kolbasov.BudgetBuddy.service.CurrencyConverterService;
import by.kolbasov.BudgetBuddy.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LimitRepository limitRepository;

    @Mock
    private CurrencyConverterService currencyConverterService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTransaction_setLimitExceededTrue(){
        TransactionRequest transactionRequest = new TransactionRequest(9999999999L,1234567890L, BigDecimal.valueOf(1500),"USD", ExpenseCategory.product);
        Limit limit = Limit.builder()
                .accountFrom(9999999999L)
                .expenseCategory(ExpenseCategory.product)
                .limitDateTime(ZonedDateTime.now())
                .limitCurrency("USD")
                .limitSum(BigDecimal.valueOf(1000))
                .remainingLimit(BigDecimal.valueOf(1000))
                .build();

        ConversionResult conversionResult = new ConversionResult(BigDecimal.valueOf(1500), 1);

        when(limitRepository.findTopByAccountFromAndExpenseCategoryOrderByLimitDateTimeDesc(any(), any()))// определяем поведение moka
                .thenReturn(Optional.of(limit));
        when(currencyConverterService.convertCurrency(any(), any()))
                .thenReturn(conversionResult);

        transactionService.saveTransaction(transactionRequest);

        ArgumentCaptor<Transaction> transaction = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transaction.capture());

        Transaction savedTransaction = transaction.getValue();
        assertTrue(savedTransaction.isLimitExceeded(), "Expected limitExceeded to be true when limit is exceeded");
    }

    @Test
    void saveTransaction_setLimitExceededFalse(){
        TransactionRequest transactionRequest = new TransactionRequest(9999999999L,1234567890L, BigDecimal.valueOf(1500),"USD", ExpenseCategory.product);
        Limit limit = Limit.builder()
                .accountFrom(9999999999L)
                .expenseCategory(ExpenseCategory.product)
                .limitDateTime(ZonedDateTime.now())
                .limitCurrency("USD")
                .limitSum(BigDecimal.valueOf(1000))
                .remainingLimit(BigDecimal.valueOf(1000))
                .build();

        ConversionResult conversionResult = new ConversionResult(BigDecimal.valueOf(900), 1);

        when(limitRepository.findTopByAccountFromAndExpenseCategoryOrderByLimitDateTimeDesc(any(), any()))// определяем поведение moka
                .thenReturn(Optional.of(limit));
        when(currencyConverterService.convertCurrency(any(), any()))
                .thenReturn(conversionResult);

        transactionService.saveTransaction(transactionRequest);

        ArgumentCaptor<Transaction> transaction = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transaction.capture());

        Transaction savedTransaction = transaction.getValue();
        assertFalse(savedTransaction.isLimitExceeded(), "Expected limitExceeded to be false when limit is not exceeded");
    }





}

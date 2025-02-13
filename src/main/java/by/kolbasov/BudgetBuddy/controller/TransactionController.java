package by.kolbasov.BudgetBuddy.controller;


import by.kolbasov.BudgetBuddy.DTO.ExceededTransactionDTO;
import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import by.kolbasov.BudgetBuddy.service.TransactionService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
@Tag(name = "transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    @PostMapping("/saveTransaction")
    public ResponseEntity<Map<String,String>> saveTransaction(@Valid @RequestBody TransactionRequest transactionRequest)
    {
        transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Transaction saved successfully"));
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<ExceededTransactionDTO>> getTransaction(@RequestParam Long accountFrom)
    {
       return ResponseEntity.ok(transactionRepository.findExceededTransactionDetails(accountFrom));

    }
}

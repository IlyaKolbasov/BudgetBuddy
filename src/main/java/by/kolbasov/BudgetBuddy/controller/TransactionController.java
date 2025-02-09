package by.kolbasov.BudgetBuddy.controller;


import by.kolbasov.BudgetBuddy.DTO.ExceededTransactionDTO;
import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.repository.TransactionRepository;
import by.kolbasov.BudgetBuddy.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    @PostMapping("/saveTransaction")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionRequest transactionRequest)
    {
        transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.ok("SAVE");
    }

    @GetMapping("/getTransaction")
    public ResponseEntity<List<ExceededTransactionDTO>> getTransaction(@RequestParam Long accountFrom)
    {
       return ResponseEntity.ok(transactionRepository.findExceededTransactionDetails(accountFrom));

    }
}

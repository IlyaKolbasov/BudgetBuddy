package by.kolbasov.BudgetBuddy.controller;


import by.kolbasov.BudgetBuddy.DTO.TransactionRequest;
import by.kolbasov.BudgetBuddy.entity.Transaction;
import by.kolbasov.BudgetBuddy.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping("/saveTransaction")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionRequest transactionRequest)
    {
        transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.ok("SAVE");
    }
}

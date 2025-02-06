package by.kolbasov.BudgetBuddy.controller;

import by.kolbasov.BudgetBuddy.DTO.LimitRequest;
import by.kolbasov.BudgetBuddy.service.ExchangeRateService;
import by.kolbasov.BudgetBuddy.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {
    @Autowired
    private  LimitService limitService;


    @PostMapping("/saveLimit")
    public ResponseEntity<String> save(@RequestBody LimitRequest limitRequest) {

        limitService.saveLimit(limitRequest);
        return ResponseEntity.ok("SAVED LIMIT");
    }

}

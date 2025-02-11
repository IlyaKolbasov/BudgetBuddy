package by.kolbasov.BudgetBuddy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class AccountNumberValidator implements ConstraintValidator<ValidAccountNumber, Long> {

    @Override
    public void initialize(ValidAccountNumber constraintAnnotation) {
    }
    @Override
    public boolean isValid(Long accountFrom, ConstraintValidatorContext context) {
        if (accountFrom == null) {
            return true;
        }
        String accountStr = Long.toString(accountFrom);
        return accountStr.length() == 10;
    }
}

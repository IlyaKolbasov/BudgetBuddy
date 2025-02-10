package by.kolbasov.BudgetBuddy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccountNumber {
    String message() default "Account number must be exactly 10 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

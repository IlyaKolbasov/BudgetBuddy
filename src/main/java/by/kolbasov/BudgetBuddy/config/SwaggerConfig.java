package by.kolbasov.BudgetBuddy.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .servers(List.of(new Server().url("https://budgetbuddy-won0.onrender.com")))
                .info(
                        new Info().title("Budget Buddy API")
                );
    }
}

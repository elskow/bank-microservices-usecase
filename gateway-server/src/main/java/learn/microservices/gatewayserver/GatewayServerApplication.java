package learn.microservices.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {
    private static final String ACCOUNTS_SERVICE_ID = "lb://ACCOUNTS";
    private static final String LOANS_SERVICE_ID = "lb://LOANS";
    private static final String CARDS_SERVICE_ID = "lb://CARDS";

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator bankLoanRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/accounts/**")
                        .filters(f -> f
                                .rewritePath("/api/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(c -> c.setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(2), 2, true))
                        )
                        .uri(ACCOUNTS_SERVICE_ID))

                .route(p -> p
                        .path("/api/customers/**")
                        .filters(f -> f
                                .rewritePath("/api/customers/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(c -> c.setName("customersCircuitBreaker").setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(2), 2, true))
                        )
                        .uri(ACCOUNTS_SERVICE_ID))

                .route(p -> p
                        .path("/api/loans/**")
                        .filters(f -> f
                                .rewritePath("/api/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(c -> c.setName("loansCircuitBreaker").setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(2), 2, true))
                        )
                        .uri(LOANS_SERVICE_ID))

                .route(p -> p
                        .path("/api/cards/**")
                        .filters(f -> f
                                .rewritePath("/api/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(c -> c.setName("cardsCircuitBreaker").setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(2), 2, true))
                        )
                        .uri(CARDS_SERVICE_ID))

                .build();
    }
}

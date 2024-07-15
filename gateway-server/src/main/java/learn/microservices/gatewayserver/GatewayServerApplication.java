package learn.microservices.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri(ACCOUNTS_SERVICE_ID))

                .route(p -> p
                        .path("/api/customers/**")
                        .filters(f -> f
                                .rewritePath("/api/customers/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri(ACCOUNTS_SERVICE_ID))

                .route(p -> p
                        .path("/api/loans/**")
                        .filters(f -> f
                                .rewritePath("/api/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri(LOANS_SERVICE_ID))

                .route(p -> p
                        .path("/api/cards/**")
                        .filters(f -> f
                                .rewritePath("/api/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri(CARDS_SERVICE_ID))

                .build();
    }
}

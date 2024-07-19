package learn.microservices.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@EnableCaching
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
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
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
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
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
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
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
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri(CARDS_SERVICE_ID))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5, 7, 6);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
                Optional.ofNullable(exchange
                        .getRequest()
                        .getHeaders()
                        .getFirst("X-User-Type")
                ).orElseGet(() -> Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress())
        );
    }
}

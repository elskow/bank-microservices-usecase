package learn.microservices.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    final FilterUtility filterUtility;

    public RequestTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (filterUtility.getCoorelationId(exchange.getRequest().getHeaders()) != null) {
            logger.debug("incoming request correlation id: {}", filterUtility.getCoorelationId(exchange.getRequest().getHeaders()));
        } else {
            String correlationId = UUID.randomUUID().toString();
            exchange = filterUtility.setCooRelationId(exchange, correlationId);
            logger.debug("outgoing request correlation id: {}", correlationId);
        }
        return chain.filter(exchange);
    }
}

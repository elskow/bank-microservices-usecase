package learn.microservices.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.Optional;

@Component
public class FilterUtility {
    public static final String COORELATION_ID = "bankloan-correlation-id";

    public String getCoorelationId(HttpHeaders requestHeaders) {
        return Optional.ofNullable(requestHeaders.get(COORELATION_ID))
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String key, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(key, value).build()).build();
    }

    public ServerWebExchange setCooRelationId(ServerWebExchange exchange, String coorelationId) {
        return this.setRequestHeader(exchange, COORELATION_ID, coorelationId);
    }
}
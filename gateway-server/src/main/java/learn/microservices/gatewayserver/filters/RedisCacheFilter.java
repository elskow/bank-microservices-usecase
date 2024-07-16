package learn.microservices.gatewayserver.filters;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Collectors;

@Order()
@Component
public class RedisCacheFilter implements GlobalFilter {

    private final ReactiveStringRedisTemplate redisTemplate;

    public RedisCacheFilter(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String generateCacheKey(String url) {
        return "cache:" + url.hashCode();
    }

    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        if (!exchange.getRequest().getMethod().matches("GET")) {
            return chain.filter(exchange);
        }

        String key = generateCacheKey(exchange.getRequest().getURI().toString());

        return redisTemplate.opsForValue().get(key)
                .flatMap(cachedResponse -> {
                    if (exchange.getResponse().isCommitted()) {
                        return Mono.empty();
                    }
                    exchange.getResponse().setStatusCode(HttpStatus.OK);

                    String contentType = Objects.requireNonNull(exchange.getResponse().getHeaders().getContentType()).toString();

                    exchange.getResponse().getHeaders().add("Content-Type", contentType);
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(cachedResponse.getBytes(StandardCharsets.UTF_8))));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    ServerHttpResponse originalResponse = exchange.getResponse();
                    DataBufferFactory bufferFactory = originalResponse.bufferFactory();
                    ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                        @Override
                        public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                            if (this.isCommitted()) {
                                return Mono.empty();
                            }
                            Flux<DataBuffer> flux = Flux.from(body);
                            return super.writeWith(flux.buffer().publishOn(Schedulers.boundedElastic()).map(dataBuffers -> {
                                String responseBody = dataBuffers.stream()
                                        .map(dataBuffer -> {
                                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                            dataBuffer.read(bytes);
                                            DataBufferUtils.release(dataBuffer);
                                            return new String(bytes, StandardCharsets.UTF_8);
                                        })
                                        .collect(Collectors.joining());
                                redisTemplate.opsForValue().set(key, responseBody).subscribe();
                                redisTemplate.expire(key, Duration.ofMinutes(10)).subscribe();
                                return bufferFactory.wrap(responseBody.getBytes(StandardCharsets.UTF_8));
                            }));
                        }
                    };
                    return chain.filter(exchange.mutate().response(decoratedResponse).build()).onErrorResume(UnsupportedOperationException.class, e -> chain.filter(exchange));
                }));
    }
}

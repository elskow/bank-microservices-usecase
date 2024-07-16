package learn.microservices.gatewayserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @RequestMapping("/contact-support")
    public Mono<ResponseEntity<String>> contactSupport() {
        return Mono.just(
                ResponseEntity
                        .internalServerError()
                        .body("An error occurred. Please contact support."));
    }
}

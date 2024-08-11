package learn.microservices.accounts.service.client;

import learn.microservices.accounts.dto.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cards", url = "http://cards:9000")
public interface CardFeignClient {

    @GetMapping(value = "/api/cards", consumes = "application/json")
    ResponseEntity<CardDTO> getCardDetail(@RequestParam String nik);

}

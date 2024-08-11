package learn.microservices.accounts.service.client;

import learn.microservices.accounts.dto.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans", url = "http://loans:8090")
public interface LoanFeignClient {

    @GetMapping(value = "/api/loans", consumes = "application/json")
    ResponseEntity<LoanDTO> getLoanDetail(@RequestParam String nik);
}

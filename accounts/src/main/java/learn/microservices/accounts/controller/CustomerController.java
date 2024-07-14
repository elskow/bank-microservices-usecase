package learn.microservices.accounts.controller;

import jakarta.validation.constraints.Size;
import learn.microservices.accounts.dto.CustomerDetailDTO;
import learn.microservices.accounts.service.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@Validated
public class CustomerController {
    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<CustomerDetailDTO> fetchCustomerDetail(@RequestParam
                                                                 @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                                 String nik) {
        CustomerDetailDTO customerDetailDTO = iCustomerService.getCustomerDetail(nik);

        return ResponseEntity.ok(customerDetailDTO);
    }
}

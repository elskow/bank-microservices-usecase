package learn.microservices.accounts.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import learn.microservices.accounts.constants.AccountConstants;
import learn.microservices.accounts.dto.CustomerDTO;
import learn.microservices.accounts.dto.ResponseDTO;
import learn.microservices.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {
    private IAccountService iAccountService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody
                                                     @Valid
                                                     CustomerDTO customerDTO) {
        iAccountService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.MESSAGE_201, AccountConstants.STATUS_201));

    }

    @GetMapping("/accounts")
    public ResponseEntity<CustomerDTO> getAccountbyNik(@RequestParam
                                                       @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                       String nik) {
        CustomerDTO customerDTO = iAccountService.getAccountByNik(nik);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }

    @PutMapping("/accounts")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody
                                                     @Valid
                                                     CustomerDTO customerDTO) {
        boolean isUpdated = iAccountService.updateAccount(customerDTO);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(AccountConstants.STATUS_304, AccountConstants.MESSAGE_304));
        }
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                     @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                     String nik) {
        boolean isDeleted = iAccountService.deleteAccount(nik);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(AccountConstants.STATUS_304, AccountConstants.MESSAGE_304));
        }
    }
}

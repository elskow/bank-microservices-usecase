package com.helmyl.accounts.controller;

import com.helmyl.accounts.constants.AccountConstants;
import com.helmyl.accounts.dto.CustomerDTO;
import com.helmyl.accounts.dto.ResponseDTO;
import com.helmyl.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountController {
    private IAccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO){
        accountService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.MESSAGE_201, AccountConstants.STATUS_201));

    }

    @GetMapping("/accounts")
    public ResponseEntity<CustomerDTO> getAccountbyEmail(@RequestParam String email) {
        CustomerDTO customerDTO = accountService.getAccountByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }
}

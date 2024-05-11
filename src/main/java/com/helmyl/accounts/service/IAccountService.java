package com.helmyl.accounts.service;

import com.helmyl.accounts.dto.CustomerDTO;

public interface IAccountService {
    /*
        * Create a new account
        *
        * @param customerDTO - CustomerDTO object
     */
    void createAccount(CustomerDTO customerDTO);
}

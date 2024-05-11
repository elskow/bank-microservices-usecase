package com.helmyl.accounts.service;

import com.helmyl.accounts.dto.CustomerDTO;

public interface IAccountService {
    /*
        * Create a new account
        *
        * @param customerDTO - CustomerDTO object
     */
    void createAccount(CustomerDTO customerDTO);


    /**
     * Get account by email
     *
     * @param email - email of the account
     * @return CustomerDTO object
     */
    CustomerDTO getAccountByEmail(String email);


    /**
     * Update account
     *
     * @param customerDTO - CustomerDTO object
     * @return boolean
     */
    boolean updateAccount(CustomerDTO customerDTO);
}

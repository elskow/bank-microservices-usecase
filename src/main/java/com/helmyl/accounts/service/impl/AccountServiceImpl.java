package com.helmyl.accounts.service.impl;

import com.helmyl.accounts.constants.AccountConstants;
import com.helmyl.accounts.dto.AccountDTO;
import com.helmyl.accounts.dto.CustomerDTO;
import com.helmyl.accounts.entity.Account;
import com.helmyl.accounts.entity.Customer;
import com.helmyl.accounts.exception.CustomerAlreadyExistException;
import com.helmyl.accounts.exception.ResourceNotFoundException;
import com.helmyl.accounts.mapper.AccountMapper;
import com.helmyl.accounts.mapper.CustomerMapper;
import com.helmyl.accounts.repository.AccountRepository;
import com.helmyl.accounts.repository.CustomerRepository;
import com.helmyl.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * Create a new account
     *
     * @param customerDTO - CustomerDTO object
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());

        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Email has already been registered");
        }

        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * Get account by email
     *
     * @param email - email of the account
     * @return CustomerDTO object
     */
    @Override
    public CustomerDTO getAccountByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "email", email)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId())
        );

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account, new AccountDTO()));

        return customerDTO;
    }

    /**
     * Create a new account
     *
     * @param customer - Customer object
     * @return Account object
     */
    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());

        long randomAccountNumber = (long) (Math.random() * 100000000000000L);

        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());

        return newAccount;
    }
}

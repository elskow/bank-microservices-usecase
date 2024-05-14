package learn.microservices.accounts.service.impl;

import learn.microservices.accounts.constants.AccountConstants;
import learn.microservices.accounts.dto.AccountDTO;
import learn.microservices.accounts.dto.CustomerDTO;
import learn.microservices.accounts.entity.Account;
import learn.microservices.accounts.entity.Customer;
import learn.microservices.accounts.exception.CustomerAlreadyExistException;
import learn.microservices.accounts.exception.ResourceNotFoundException;
import learn.microservices.accounts.mapper.AccountMapper;
import learn.microservices.accounts.mapper.CustomerMapper;
import learn.microservices.accounts.repository.AccountRepository;
import learn.microservices.accounts.repository.CustomerRepository;
import learn.microservices.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
     * Update account
     *
     * @param customerDTO - CustomerDTO object
     * @return boolean
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountDTO accountDTO = customerDTO.getAccountDTO();

        if (accountDTO != null) {
            Account account = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDTO.getAccountNumber())
            );
            AccountMapper.mapToAccount(accountDTO, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId)
            );

            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "email", email)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.delete(customer);

        return true;
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

        return newAccount;
    }
}

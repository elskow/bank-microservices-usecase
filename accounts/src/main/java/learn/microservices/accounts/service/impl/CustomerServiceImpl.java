package learn.microservices.accounts.service.impl;

import learn.microservices.accounts.dto.AccountDTO;
import learn.microservices.accounts.dto.CardDTO;
import learn.microservices.accounts.dto.CustomerDetailDTO;
import learn.microservices.accounts.dto.LoanDTO;
import learn.microservices.accounts.entity.Account;
import learn.microservices.accounts.entity.Customer;
import learn.microservices.accounts.exception.ResourceNotFoundException;
import learn.microservices.accounts.mapper.AccountMapper;
import learn.microservices.accounts.mapper.CustomerMapper;
import learn.microservices.accounts.repository.AccountRepository;
import learn.microservices.accounts.repository.CustomerRepository;
import learn.microservices.accounts.service.ICustomerService;
import learn.microservices.accounts.service.client.CardFeignClient;
import learn.microservices.accounts.service.client.LoanFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    private CardFeignClient cardFeignClient;
    private LoanFeignClient loanFeignClient;

    /**
     * Get customer detail by NIK
     *
     * @return CustomerDetailDTO
     */
    @Override
    public CustomerDetailDTO getCustomerDetail(String nik, String correlationId) {
        Customer customer = customerRepository.findByNik(nik).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "NIK", nik)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "NIK", nik)
        );

        CustomerDetailDTO customerDetailDTO = CustomerMapper.mapToCustomerDetailDTO(customer, new CustomerDetailDTO());
        customerDetailDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account, new AccountDTO()));

        ResponseEntity<CardDTO> cardResponse = cardFeignClient.getCardDetail(nik, correlationId);
        customerDetailDTO.setCardDTO(cardResponse.getBody());

        ResponseEntity<LoanDTO> loanResponse = loanFeignClient.getLoanDetail(nik, correlationId);
        customerDetailDTO.setLoanDTO(loanResponse.getBody());

        return customerDetailDTO;
    }
}

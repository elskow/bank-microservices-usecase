package com.helmyl.accounts.mapper;

import com.helmyl.accounts.dto.CustomerDTO;
import com.helmyl.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }
}

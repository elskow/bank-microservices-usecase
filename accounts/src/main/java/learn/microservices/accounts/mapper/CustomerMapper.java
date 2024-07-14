package learn.microservices.accounts.mapper;

import learn.microservices.accounts.dto.CustomerDTO;
import learn.microservices.accounts.dto.CustomerDetailDTO;
import learn.microservices.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setName(customer.getName());
        customerDTO.setNik(customer.getNik());
        return customerDTO;
    }

    public static CustomerDetailDTO mapToCustomerDetailDTO(Customer customer, CustomerDetailDTO customerDetailDTO) {
        customerDetailDTO.setName(customer.getName());
        customerDetailDTO.setNik(customer.getNik());
        return customerDetailDTO;
    }

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setName(customerDTO.getName());
        customer.setNik(customerDTO.getNik());
        return customer;
    }
}

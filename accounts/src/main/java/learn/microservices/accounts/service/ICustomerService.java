package learn.microservices.accounts.service;

import learn.microservices.accounts.dto.CustomerDetailDTO;

public interface ICustomerService {
    /**
     * Get customer detail by NIK
     *
     * @return CustomerDetailDTO
     */
    CustomerDetailDTO getCustomerDetail(String nik);
}

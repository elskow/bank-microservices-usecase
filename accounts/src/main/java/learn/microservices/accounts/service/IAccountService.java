package learn.microservices.accounts.service;

import learn.microservices.accounts.dto.CustomerDTO;

public interface IAccountService {
    /*
     * Create a new account
     *
     * @param customerDTO - CustomerDTO object
     */
    void createAccount(CustomerDTO customerDTO);


    /**
     * Get account by NIK
     *
     * @param nik - nik of the account
     * @return CustomerDTO object
     */
    CustomerDTO getAccountByNik(String nik);


    /**
     * Update account
     *
     * @param customerDTO - CustomerDTO object
     * @return boolean
     */
    boolean updateAccount(CustomerDTO customerDTO);

    /**
     * Delete account
     *
     * @param nik - nik of the account
     * @return boolean
     */
    boolean deleteAccount(String nik);
}

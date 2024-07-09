package learn.microservices.loanservice.service;

import learn.microservices.loanservice.dto.LoanDTO;

public interface ILoanService {
    /**
     * Create a loan for a customer with the given NIK.
     *
     * @param nik the NIK of the customer
     */
    void createLoan(String nik);

    /**
     * Get a loan by the given NIK.
     *
     * @param nik the NIK of the customer
     * @return the loan
     */
    LoanDTO getLoanByNik(String nik);

    /**
     * Update the loan with the given data.
     *
     * @param loanDTO the loan data
     * @return true if the loan is updated successfully, false otherwise
     */
    boolean updateLoan(LoanDTO loanDTO);

    /**
     * Delete the loan with the given NIK.
     *
     * @param nik the NIK of the customer
     * @return true if the loan is deleted successfully, false otherwise
     */
    boolean deleteLoan(String nik);
}

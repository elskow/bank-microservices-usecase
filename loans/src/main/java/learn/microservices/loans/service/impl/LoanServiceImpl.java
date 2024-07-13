package learn.microservices.loans.service.impl;

import learn.microservices.loans.constants.LoanConstants;
import learn.microservices.loans.dto.LoanDTO;
import learn.microservices.loans.entity.Loan;
import learn.microservices.loans.mapper.LoanMapper;
import learn.microservices.loans.repository.LoanRepository;
import learn.microservices.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {
    private LoanRepository loanRepository;

    /**
     * Create a loan for a customer with the given NIK.
     *
     * @param nik the NIK of the customer
     */
    @Override
    public void createLoan(String nik) {
        Optional<Loan> loanOptional = loanRepository.findByNik(nik);
        if (loanOptional.isPresent()) {
            throw new RuntimeException("Loan already exists for the customer with NIK: " + nik);
        }

        Loan newLoan = new Loan();
        newLoan.setNik(nik);

        long loanNumber = 0;
        boolean isUnique = false;

        while (!isUnique) {
            loanNumber = (long) (Math.random() * 1000000000000L);
            isUnique = loanRepository.findByLoanNumber(String.valueOf(loanNumber)).isEmpty();
        }

        newLoan.setLoanNumber(String.valueOf(loanNumber));
        newLoan.setLoanType(LoanConstants.STUDENT_LOAN);
        newLoan.setTotalLoan(LoanConstants.LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.LOAN_LIMIT);

        loanRepository.save(newLoan);
    }

    /**
     * Get a loan by the given NIK.
     *
     * @param nik the NIK of the customer
     * @return the loan
     */
    @Override
    public LoanDTO getLoanByNik(String nik) {
        Loan loan = loanRepository.findByNik(nik)
                .orElseThrow(() -> new RuntimeException("Loan not found for the customer with NIK: " + nik));

        return LoanMapper.mapToLoanDTO(loan, new LoanDTO());
    }

    /**
     * Update the loan with the given data.
     *
     * @param loanDTO the loan data
     * @return true if the loan is updated successfully, false otherwise
     */
    @Override
    public boolean updateLoan(LoanDTO loanDTO) {
        Loan loan = loanRepository.findByNik(loanDTO.getNik())
                .orElseThrow(() -> new RuntimeException("Loan not found for the customer with NIK: " + loanDTO.getNik()));

        LoanMapper.mapToLoan(loanDTO, loan);
        loanRepository.save(loan);
        return true;
    }

    /**
     * Delete the loan with the given NIK.
     *
     * @param nik the NIK of the customer
     * @return true if the loan is deleted successfully, false otherwise
     */
    @Override
    public boolean deleteLoan(String nik) {
        Loan loan = loanRepository.findByNik(nik)
                .orElseThrow(() -> new RuntimeException("Loan not found for the customer with NIK: " + nik));

        loanRepository.delete(loan);
        return true;
    }
}

package learn.microservices.loans.mapper;

import learn.microservices.loans.dto.LoanDTO;
import learn.microservices.loans.entity.Loan;

public class LoanMapper {
    public static LoanDTO mapToLoanDTO(Loan loan, LoanDTO loanDTO) {
        loanDTO.setNik(loan.getNik());
        loanDTO.setLoanNumber(loan.getLoanNumber());
        loanDTO.setLoanType(loan.getLoanType());
        loanDTO.setTotalLoan(loan.getTotalLoan());
        loanDTO.setAmountPaid(loan.getAmountPaid());
        loanDTO.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDTO;
    }

    public static void mapToLoan(LoanDTO loanDTO, Loan loan) {
        loan.setNik(loanDTO.getNik());
        loan.setLoanNumber(loanDTO.getLoanNumber());
        loan.setLoanType(loanDTO.getLoanType());
        loan.setTotalLoan(loanDTO.getTotalLoan());
        loan.setAmountPaid(loanDTO.getAmountPaid());
        loan.setOutstandingAmount(loanDTO.getOutstandingAmount());
    }
}

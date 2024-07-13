package learn.microservices.loans.repository;

import learn.microservices.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByNik(String nik);

    Optional<Loan> findByLoanNumber(String loanNumber);
}

package learn.microservices.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoanDTO {
    @NotEmpty(message = "NIK is required")
    @Pattern(regexp = "^[0-9]{16}$", message = "NIK must be 16 digits and numeric")
    private String nik;

    @NotEmpty(message = "Loan number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "Loan number must be 12 digits and numeric")
    private String loanNumber;

    @NotEmpty(message = "Loan type is required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Loan type must be alphanumeric")
    private String loanType;

    @PositiveOrZero(message = "Total loan must be positive or zero")
    private int totalLoan;

    @PositiveOrZero(message = "Amount paid must be positive or zero")
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding amount must be positive or zero")
    private int outstandingAmount;
}

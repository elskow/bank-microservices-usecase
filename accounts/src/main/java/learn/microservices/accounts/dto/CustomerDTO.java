package learn.microservices.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name should have atleast 2 characters")
    private String name;

    @NotEmpty(message = "NIK is required")
    @Pattern(regexp = "^[0-9]{16}$", message = "NIK should have 16 digits and only numbers are allowed")
    private String nik;

    private AccountDTO accountDTO;
}

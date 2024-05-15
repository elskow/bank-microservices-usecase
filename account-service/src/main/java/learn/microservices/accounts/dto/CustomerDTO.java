package learn.microservices.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name should have atleast 2 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    private AccountDTO accountDTO;
}

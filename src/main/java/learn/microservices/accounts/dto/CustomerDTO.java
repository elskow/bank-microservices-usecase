package learn.microservices.accounts.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private String name;
    private String email;

    private AccountDTO accountDTO;
}

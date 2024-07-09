package learn.microservices.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardDTO {
    @NotEmpty(message = "NIK cannot be empty")
    @Pattern(regexp = "^$|[0-9]{16}", message = "NIK should be 16 digits")
    private String nik;

    @NotEmpty(message = "Card number cannot be empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card number should be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be empty")
    private String cardType;

    @PositiveOrZero(message = "Total limit should be positive or zero")
    private int totalLimit;

    @PositiveOrZero(message = "Amount used should be positive or zero")
    private int amountUsed;

    @PositiveOrZero(message = "Available amount should be positive or zero")
    private int availableAmount;
}

package learn.microservices.cards.constants;

public class CardConstants {
    public static final String CREDIT_CARD = "Credit Card";
    public static final String DEBIT_CARD = "Debit Card";
    public static final int CREDIT_LIMIT = 1_000_000;
    public static final int DEBIT_LIMIT = 100_000;

    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Account created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Account Processed successfully";
    public static final String STATUS_304 = "304";
    public static final String MESSAGE_304 = "Account not updated";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "Internal Server Error";

    private CardConstants() {
        // Restrict instantiation
    }
}

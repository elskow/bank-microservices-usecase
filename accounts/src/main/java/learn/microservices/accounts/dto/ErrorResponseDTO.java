package learn.microservices.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorResponseDTO {
    private String apiPath;

    private HttpStatus errorCode;
    private Object errorMessage;
    private LocalDateTime errorTime;
}

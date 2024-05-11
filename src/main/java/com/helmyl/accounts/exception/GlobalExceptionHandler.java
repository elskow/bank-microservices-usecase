package com.helmyl.accounts.exception;

import com.helmyl.accounts.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex, WebRequest req){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                req.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}

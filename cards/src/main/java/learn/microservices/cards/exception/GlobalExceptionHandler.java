package learn.microservices.cards.exception;

import jakarta.validation.ConstraintViolationException;
import learn.microservices.cards.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            WebRequest req) {
        String endpoint = req.getDescription(false);
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        ErrorResponseDTO errorDto = new ErrorResponseDTO(endpoint,
                HttpStatus.BAD_REQUEST, validationErrors,
                LocalDateTime.now());

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request) {
        String endpoint = request.getDescription(false);
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            errors.put(propertyPath.substring(propertyPath.indexOf(".") + 1),
                    constraintViolation.getMessage());
        });
        try {
            ErrorResponseDTO errorDto = new ErrorResponseDTO(endpoint,
                    HttpStatus.BAD_REQUEST, errors,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ErrorResponseDTO errorDto = new ErrorResponseDTO(endpoint,
                    HttpStatus.BAD_REQUEST, ex.getMessage(),
                    LocalDateTime.now());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex, WebRequest req) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                req.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CardAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleCardAlreadyExistException(CardAlreadyExistException ex, WebRequest req) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                req.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                req.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}

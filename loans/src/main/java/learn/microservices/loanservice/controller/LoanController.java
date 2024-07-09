package learn.microservices.loanservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import learn.microservices.loanservice.constants.LoanConstants;
import learn.microservices.loanservice.dto.LoanDTO;
import learn.microservices.loanservice.dto.ResponseDTO;
import learn.microservices.loanservice.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoanController {
    private final ILoanService iLoanService;

    @PostMapping("/loans")
    public ResponseEntity<ResponseDTO> createCard(@RequestParam
                                                  @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                  String nik) {
        iLoanService.createLoan(nik);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(LoanConstants.MESSAGE_201, LoanConstants.STATUS_201));
    }

    @GetMapping("/loans")
    public ResponseEntity<LoanDTO> getLoanByNik(@RequestParam
                                                @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                String nik) {
        LoanDTO loanDTO = iLoanService.getLoanByNik(nik);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDTO);
    }

    @PutMapping("/loans")
    public ResponseEntity<ResponseDTO> updateLoan(@RequestBody
                                                  @Valid
                                                  LoanDTO loanDTO) {
        boolean isUpdated = iLoanService.updateLoan(loanDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(LoanConstants.STATUS_304, LoanConstants.MESSAGE_304));
        }
    }

    @DeleteMapping("/loans")
    public ResponseEntity<ResponseDTO> deleteLoan(@RequestParam
                                                  @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                  String nik) {
        boolean isDeleted = iLoanService.deleteLoan(nik);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(LoanConstants.STATUS_304, LoanConstants.MESSAGE_304));
        }
    }
}

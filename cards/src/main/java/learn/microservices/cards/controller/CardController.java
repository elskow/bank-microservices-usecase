package learn.microservices.cards.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import learn.microservices.cards.constants.CardConstants;
import learn.microservices.cards.dto.CardDTO;
import learn.microservices.cards.dto.ResponseDTO;
import learn.microservices.cards.service.ICardService;
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
public class CardController {
    private final ICardService iCardService;

    @PostMapping("/cards")
    public ResponseEntity<ResponseDTO> createCard(@RequestParam
                                                  @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                  String nik) {
        iCardService.createCard(nik);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(CardConstants.MESSAGE_201, CardConstants.STATUS_201));
    }

    @GetMapping("/cards")
    public ResponseEntity<CardDTO> getCardByNik(@RequestParam
                                                @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                String nik) {
        CardDTO cardDTO = iCardService.getCardByNik(nik);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardDTO);
    }

    @PutMapping("/cards")
    public ResponseEntity<ResponseDTO> updateCard(@RequestBody
                                                  @Valid
                                                  CardDTO cardDTO) {
        boolean isUpdated = iCardService.updateCard(cardDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(CardConstants.STATUS_304, CardConstants.MESSAGE_304));
        }
    }

    @DeleteMapping("/cards")
    public ResponseEntity<ResponseDTO> deleteCard(@RequestParam
                                                  @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                  String nik) {
        boolean isDeleted = iCardService.deleteCard(nik);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(CardConstants.STATUS_304, CardConstants.MESSAGE_304));
        }
    }
}

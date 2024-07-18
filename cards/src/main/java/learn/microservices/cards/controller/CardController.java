package learn.microservices.cards.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import learn.microservices.cards.constants.CardConstants;
import learn.microservices.cards.dto.CardDTO;
import learn.microservices.cards.dto.DevInfoDTO;
import learn.microservices.cards.dto.ResponseDTO;
import learn.microservices.cards.service.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RefreshScope
public class CardController {
    private final static Logger logger = LoggerFactory.getLogger(CardController.class);

    private final ICardService iCardService;

    private final Environment environment;
    private final DevInfoDTO devInfoDTO;

    @Value("${build.version}")
    private String buildInfo;

    public CardController(ICardService iCardService, Environment environment, DevInfoDTO devInfoDTO) {
        this.iCardService = iCardService;
        this.environment = environment;
        this.devInfoDTO = devInfoDTO;
    }

    @PostMapping("/cards")
    public ResponseEntity<ResponseDTO> createCard(@RequestParam
                                                  @Size(min = 16, max = 16, message = "NIK should have 16 characters")
                                                  String nik) {
        logger.debug("start-create-card {}", nik);
        iCardService.createCard(nik);
        logger.debug("end-create-card {}", nik);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(CardConstants.MESSAGE_201, CardConstants.STATUS_201));
    }

    @GetMapping("/cards")
    public ResponseEntity<CardDTO> getCardByNik(
            @RequestParam @Size(min = 16, max = 16, message = "NIK should have 16 characters") String nik) {
        logger.debug("start-fetch-card {}", nik);
        CardDTO cardDTO = iCardService.getCardByNik(nik);
        logger.debug("end-fetch-card {}", nik);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardDTO);
    }

    @PutMapping("/cards")
    public ResponseEntity<ResponseDTO> updateCard(@RequestBody
                                                  @Valid
                                                  CardDTO cardDTO) {
        logger.debug("start-update-card {}", cardDTO.getNik());
        boolean isUpdated = iCardService.updateCard(cardDTO);
        logger.debug("end-update-card {}", cardDTO.getNik());
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
        logger.debug("start-delete-card {}", nik);
        boolean isDeleted = iCardService.deleteCard(nik);
        logger.debug("end-delete-card {}", nik);
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

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildInfo);
    }


    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("java.version"));
    }

    @GetMapping("/dev-info")
    public ResponseEntity<DevInfoDTO> getDevInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(devInfoDTO);
    }
}

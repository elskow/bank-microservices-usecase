package learn.microservices.cards.service.impl;

import learn.microservices.cards.dto.CardDTO;
import learn.microservices.cards.entity.Card;
import learn.microservices.cards.exception.CardAlreadyExistException;
import learn.microservices.cards.mapper.CardMapper;
import learn.microservices.cards.repository.CardRepository;
import learn.microservices.cards.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static learn.microservices.cards.constants.CardConstants.CREDIT_CARD;
import static learn.microservices.cards.constants.CardConstants.CREDIT_LIMIT;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {
    private CardRepository cardRepository;

    /**
     * Create a new card for the given NIK.
     *
     * @param nik the NIK of the customer
     */
    @Override
    public void createCard(String nik) {
        Optional<Card> cardOptional = cardRepository.findByNik(nik);
        if (cardOptional.isPresent()) {
            throw new CardAlreadyExistException("Card already exists for the given NIK: " + nik);
        }

        cardRepository.save(createNewCard(nik));
    }

    /**
     * Create a new card with a unique card number.
     *
     * @param nik the NIK of the customer
     * @return the new card
     */
    private Card createNewCard(String nik) {
        boolean isUnique = false;
        long cardNumber = 0;
        Random random = new Random();
        while (!isUnique) {
            long randomNumber = 100000000000L + (long) (random.nextDouble() * 899999999999L);
            String toHash = nik + Instant.now().toEpochMilli() + randomNumber;
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(toHash.getBytes());
                cardNumber = bytesToLong(hashBytes);
                cardNumber = Math.abs(cardNumber % 900000000000L) + 100000000000L;
                isUnique = cardRepository.findByCardNumber(String.valueOf(cardNumber)).isEmpty();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error generating card number", e);
            }
        }

        Card newCard = new Card();
        newCard.setCardNumber(String.valueOf(cardNumber));
        newCard.setNik(nik);
        newCard.setCardType(CREDIT_CARD);
        newCard.setTotalLimit(CREDIT_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CREDIT_LIMIT);

        return newCard;
    }

    /**
     * Converts the first 8 bytes of a byte array to a long value.
     */
    private long bytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) + (bytes[i] & 0xff);
        }
        return value;
    }

    /**
     * Get a card by the given NIK.
     *
     * @param nik the NIK of the customer
     * @return the card
     */
    @Override
    public CardDTO getCardByNik(String nik) {
        Card card = cardRepository.findByNik(nik)
                .orElseThrow(() -> new CardAlreadyExistException("Card does not exist for the given NIK: " + nik));
        return CardMapper.mapToCardDTO(card, new CardDTO());
    }

    /**
     * Update the card with the given data.
     *
     * @param cardDTO the card data
     * @return true if the card is updated successfully, false otherwise
     */
    @Override
    public boolean updateCard(CardDTO cardDTO) {
        Card card = cardRepository.findByNik(cardDTO.getNik())
                .orElseThrow(() -> new CardAlreadyExistException("Card does not exist for the given NIK: " + cardDTO.getNik()));
        CardMapper.mapToCard(cardDTO, card);
        cardRepository.save(card);
        return true;
    }

    /**
     * Delete the card with the given NIK.
     *
     * @param nik the NIK of the customer
     * @return true if the card is deleted successfully, false otherwise
     */
    @Override
    public boolean deleteCard(String nik) {
        Card card = cardRepository.findByNik(nik)
                .orElseThrow(() -> new CardAlreadyExistException("Card does not exist for the given NIK: " + nik));
        cardRepository.deleteById((long) card.getCardId());
        return true;
    }
}

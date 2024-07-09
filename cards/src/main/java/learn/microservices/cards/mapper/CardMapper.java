package learn.microservices.cards.mapper;

import learn.microservices.cards.dto.CardDTO;
import learn.microservices.cards.entity.Card;

public class CardMapper {
    public static CardDTO mapToCardDTO(Card card, CardDTO cardDTO) {
        cardDTO.setNik(card.getNik());
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setTotalLimit(card.getTotalLimit());
        cardDTO.setAmountUsed(card.getAmountUsed());
        cardDTO.setAvailableAmount(card.getAvailableAmount());
        return cardDTO;
    }

    public static Card mapToCard(CardDTO cardDTO, Card card) {
        card.setNik(cardDTO.getNik());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardType(cardDTO.getCardType());
        card.setTotalLimit(cardDTO.getTotalLimit());
        card.setAmountUsed(cardDTO.getAmountUsed());
        card.setAvailableAmount(cardDTO.getAvailableAmount());
        return card;
    }
}

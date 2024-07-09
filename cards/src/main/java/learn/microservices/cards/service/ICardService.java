package learn.microservices.cards.service;

import learn.microservices.cards.dto.CardDTO;

public interface ICardService {
    /**
     * Create a card for a customer with the given NIK.
     *
     * @param nik the NIK of the customer
     */
    void createCard(String nik);

    /**
     * Get a card by the given NIK.
     *
     * @param nik the NIK of the customer
     * @return the card
     */
    CardDTO getCardByNik(String nik);

    /**
     * Update the card with the given data.
     *
     * @param cardDTO the card data
     * @return true if the card is updated successfully, false otherwise
     */
    boolean updateCard(CardDTO cardDTO);

    /**
     * Delete the card with the given NIK.
     *
     * @param nik the NIK of the customer
     * @return true if the card is deleted successfully, false otherwise
     */
    boolean deleteCard(String nik);
}

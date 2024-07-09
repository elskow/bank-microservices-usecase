package learn.microservices.cards.repository;

import learn.microservices.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNik(String nik);

    Optional<Card> findByCardNumber(String cardNumber);
}

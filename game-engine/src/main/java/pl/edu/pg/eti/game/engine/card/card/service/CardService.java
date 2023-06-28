package pl.edu.pg.eti.game.engine.card.card.service;

import pl.edu.pg.eti.game.engine.card.card.entity.Card;
import pl.edu.pg.eti.game.engine.card.card.repository.CardRepository;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Card Service to communication with cards' data saved in database.
 */
@Service
public class CardService {

    /**
     * JPA repository communicating with database.
     */
    private final CardRepository cardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param cardRepository
     */
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Returns card by ID.
     *
     * @param id
     * @return card found
     */
    public Optional<Card> findCard(Integer id) {
        return cardRepository.findById(id);
    }

    /**
     * Returns all cards found.
     *
     * @return list of cards
     */
    public List<Card> findCards() {
        return cardRepository.findAll();
    }

    /**
     * Returns all cards in particular game.
     *
     * @param game - game to find cards from
     * @return list of cards in game of ID gameId
     */
    public List<Card> findCards(Game game) {
        return cardRepository.findAllByGames(game);
    }

    /**
     * Returns card of ID cardId found in particular game.
     *
     * @param game - game to find card from
     * @param cardId - identifier of card
     * @return card of ID cardId if such exists in game
     */
    public Optional<Card> findCard(Game game, Integer cardId) {
        return cardRepository.findByGamesAndId(game, cardId);
    }

}

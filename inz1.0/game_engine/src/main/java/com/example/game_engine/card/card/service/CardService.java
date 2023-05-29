package com.example.game_engine.card.card.service;

import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.repository.CardRepository;
import com.example.game_engine.game.entity.Game;
import jakarta.transaction.Transactional;
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
     * @param cardRepository
     */
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Returns card by ID. If no card found, throws exception.
     * @param id
     * @return card found
     */
    public Card findById(Integer id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return card.get();
        } else throw new RuntimeException("No card found");
    }

    /**
     * Returns all cards found.
     * @return list of cards
     */
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    /**
     * Returns all cards in particular game.
     * @param game - game to find cards from
     * @return list of cards in game of ID gameId
     */
    public List<Card> findCardsByGame(Game game) {
        return cardRepository.findAllByGames(game);
    }

    /**
     * Returns card of ID cardId found in particular game.
     * @param game - game to find card from
     * @param cardId - identifier of card
     * @return card of ID cardId if such exists in game
     */
    public Card findCardByGameIdAndCardId(Game game, Integer cardId) {
        return cardRepository.findCardByGamesAndId(game, cardId);
    }

}

package com.example.game_engine.card.card.service;

import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.repository.CardRepository;
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
     * Returns all cards in game of ID gameId.
     * @param gameId - identifier of game
     * @return list of cards in game of ID gameId
     */
    public List<Card> findAllByGameId(Integer gameId) {
        return cardRepository.findCardsByGameId(gameId);
    }

    /**
     * Returns card of ID cardId found in game of ID gameId.
     * @param gameId - identifier of game
     * @param cardId - identifier of card
     * @return card of ID cardId if such exists in game of ID gameId
     */
    public Card findCardByGameIdAndCardId(Integer gameId, Integer cardId) {
        return cardRepository.findCardByGameIdAndId(gameId, cardId);
    }

}

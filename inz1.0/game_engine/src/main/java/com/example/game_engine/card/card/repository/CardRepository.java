package com.example.game_engine.card.card.repository;

import com.example.game_engine.card.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Card and integer as id
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    /**
     * Method to retrieve all cards by gameId - all cards in particular game.
     * @param gameId - identifier of game
     * @return list of cards in game
     */
    List<Card> findCardsByGameId(Integer gameId);

    /**
     * Method to retrieve card by gameId and cardId - one card from all in particular game.
     * @param gameId - identifier of game
     * @param cardId - identifier of card
     * @return card in game of ID gameId
     */
    Card findCardByGameIdAndId(Integer gameId, Integer cardId);

}

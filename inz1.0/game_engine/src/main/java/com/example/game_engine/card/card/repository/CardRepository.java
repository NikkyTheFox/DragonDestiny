package com.example.game_engine.card.card.repository;

import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Card and integer as id
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    /**
     * Method to retrieve all cards by game - all cards in particular game.
     * @param game - game to find all cards from
     * @return list of cards in game
     */
    List<Card> findAllByGames(Game game);

    /**
     * Method to retrieve card by game and cardId - one card from all in particular game.
     * @param game - game to find card from
     * @param cardId - identifier of card
     * @return card in game
     */
    Card findCardByGamesAndId(Game game, Integer cardId);

}

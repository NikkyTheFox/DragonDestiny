package com.example.game_engine.card.card.controller;

import com.example.game_engine.card.card.dto.CardDTO;
import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.service.CardService;
import com.example.game_engine.card.enemycard.dto.EnemyCardDTO;
import com.example.game_engine.card.enemycard.entity.EnemyCard;
import com.example.game_engine.card.itemcard.dto.ItemCardDTO;
import com.example.game_engine.card.itemcard.entity.ItemCard;
import com.example.game_engine.game.entity.Game;
import com.example.game_engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents REST controller, allows to handle requests to get cards in game.
 * Requests go through /api/games/{gameId}/cards - represent cards found in game of id gameId.
 */
@RestController
@RequestMapping(value = {"/api/games/{gameId}/cards"})
public class GameCardController
{
    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * CardService used to communicate with service layer that will communicate with database repository.
     */
    private final CardService cardService;

    /**
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param cardService
     * @param gameService
     * @param modelMapper
     */
    @Autowired
    public GameCardController(CardService cardService, GameService gameService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all cards added to game of gameId ID.
     * @param gameId - game identifier
     * @return list of cards in game
     */
    @GetMapping
    public List<Card> getCards(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
       // return cardService.findAllByGameId(game.getId());
        return cardService.findCardsByGame(game);
    }

    /**
     * Retrieve card by its ID that is added to game of gameId ID.
     * @param id - identifier of card
     * @param gameId - game identifier
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable(name = "id") Integer id, @PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
       // Card card = cardService.findCardByGameIdAndCardId(gameId, id);
        Card card = cardService.findCardByGameIdAndCardId(game, id);
        if (card instanceof EnemyCard) {
            EnemyCardDTO cardResponse = modelMapper.map(card, EnemyCardDTO.class);
            return ResponseEntity.ok().body(cardResponse);
        }
        else if (card instanceof ItemCard)
        {
            ItemCardDTO cardResponse = modelMapper.map(card, ItemCardDTO.class);
            return ResponseEntity.ok().body(cardResponse);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieve all card of type EnemyCard added to game of gameId ID.
     * @param gameId - game identifier
     * @return list of EnemyCardDTOs
     */
    @GetMapping("/enemyCards")
    public List<EnemyCardDTO> getAllEnemyCardsByGame(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
       // List<Card> cards = cardService.findAllByGameId(gameId);
        List<Card> cards = cardService.findCardsByGame(game);
        List<EnemyCardDTO> enemyCards = new ArrayList<EnemyCardDTO>();
        for (Card card : cards)
        {
            if (card instanceof EnemyCard)
            {
                EnemyCardDTO cardResponse = modelMapper.map(card, EnemyCardDTO.class);
                enemyCards.add(cardResponse);
            }
        }
        return enemyCards;
    }

    /**
     * Retrieve all card of type ItemCard added to game of gameId ID.
     * @param gameId - game identifier
     * @return list of ItemCardDTOs
     */
    @GetMapping("/itemCards")
    public List<ItemCardDTO> getAllItemCardsByGame(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        List<Card> cards = cardService.findCardsByGame(game);
        List<ItemCardDTO> itemCards = new ArrayList<ItemCardDTO>();
        for (Card card : cards)
        {
            if (card instanceof ItemCard)
            {
                ItemCardDTO cardResponse = modelMapper.map(card, ItemCardDTO.class);
                itemCards.add(cardResponse);
            }
        }
        return itemCards;
    }

}
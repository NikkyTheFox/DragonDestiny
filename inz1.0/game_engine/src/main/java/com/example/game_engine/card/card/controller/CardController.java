package com.example.game_engine.card.card.controller;

import com.example.game_engine.card.card.dto.CardDTO;
import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.service.CardService;
import com.example.game_engine.card.enemycard.dto.EnemyCardDTO;
import com.example.game_engine.card.enemycard.entity.EnemyCard;
import com.example.game_engine.card.itemcard.dto.ItemCardDTO;
import com.example.game_engine.card.itemcard.entity.ItemCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get cards.
 * Requests go through /api/cards - they represent all cards, not only those assigned to games.
 */
@RestController
@RequestMapping(value = {"/api/cards"})
public class CardController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * CardService used to communicate with service layer that will communicate with database repository.
     */
    private final CardService cardService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param cardService
     * @param modelMapper
     */
    @Autowired
    CardController(CardService cardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all cards.
     * @return list of CardDTOs
     */
    @GetMapping()
    public List<CardDTO> getAllCards() {
        return cardService.findAll().stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve card by ID.
     * @param id - identifier of card
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable(name = "id") Integer id) {
        Card card = cardService.findById(id);
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
     * Retrieve all cards of type EnemyCard.
     * @return list of enemy cards
     */
    @GetMapping("/enemycards")
    public List<EnemyCardDTO> getAllEnemyCards() {
        List<Card> cards = cardService.findAll();
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
     * Retrieve all cards of type ItemCard.
     * @return list of item cards
     */
    @GetMapping("/itemcards")
    public List<ItemCardDTO> getAllItemCards() {
        List<Card> cards = cardService.findAll();
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

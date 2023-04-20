package com.example.game.card.card.controller;

import com.example.game.card.card.dto.CardDTO;
import com.example.game.card.card.entity.Card;
import com.example.game.card.card.service.CardService;
import com.example.game.card.enemycard.dto.EnemyCardDTO;
import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.itemcard.dto.ItemCardDTO;
import com.example.game.card.itemcard.entity.ItemCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api/cards"})
public class CardController {

    private ModelMapper modelMapper;
    private CardService cardService;
    @Autowired
    CardController(CardService cardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<CardDTO> getAllCards() {
        return cardService.findAll().stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
    }
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "id") Integer id) {
        cardService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
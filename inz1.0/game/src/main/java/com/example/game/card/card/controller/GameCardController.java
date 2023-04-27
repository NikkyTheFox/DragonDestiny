package com.example.game.card.card.controller;

import com.example.game.card.card.dto.CardDTO;
import com.example.game.card.card.entity.Card;
import com.example.game.card.card.service.CardService;
import com.example.game.card.enemycard.dto.EnemyCardDTO;
import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.itemcard.dto.ItemCardDTO;
import com.example.game.card.itemcard.entity.ItemCard;
import com.example.game.game.entity.Game;
import com.example.game.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api/games/{gameid}/cards"})
public class GameCardController
{
    private ModelMapper modelMapper;
    private CardService cardService;
    private GameService gameService;

    @Autowired
    public GameCardController(CardService cardService, GameService gameService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Card> getCards(@PathVariable("gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        return cardService.findAllByGameId(game.getId());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable(name = "id") Integer id, @PathVariable("gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        Card card = cardService.findCardByGameIdAndCardId(gameid, id);
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
    public List<EnemyCardDTO> getAllEnemyCardsByGame(@PathVariable("gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        List<Card> cards = cardService.findAllByGameId(gameid);
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
    public List<ItemCardDTO> getAllItemCardsByGame(@PathVariable("gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
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
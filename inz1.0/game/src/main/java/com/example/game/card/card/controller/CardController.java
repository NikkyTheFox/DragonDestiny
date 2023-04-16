package com.example.game.card.card.controller;

import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import com.example.game.card.card.dto.CardDTO;
import com.example.game.card.card.entity.Card;
import com.example.game.card.card.repository.CardRepository;
import com.example.game.card.card.service.CardService;
import com.example.game.game.dto.GameDTO;
import com.example.game.game.entity.Game;
import com.example.game.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private ModelMapper modelMapper;
    private CardRepository cardService;
    @Autowired
    CardController(CardRepository cardService, ModelMapper modelMapper) {
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
        Optional<Card> card = cardService.findById(id);
        // convert board entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    }
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO){
        // convert DTO to entity
        Card cardRequest = modelMapper.map(cardDTO, Card.class);
        Card card = cardService.save(cardRequest);
        // convert entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable(name = "id") Integer id, @RequestBody CardDTO cardDTO) {
        // convert DTO to entity
        Card cardRequest = modelMapper.map(cardDTO, Card.class);
        //Card card = cardService.findById(id);
        cardService.deleteById(id);
        // convert entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "id") Integer id) {
        cardService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
/*
@RestController
@RequestMapping("/api/cards")
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
        // convert board entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    }
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO){
        // convert DTO to entity
        Card cardRequest = modelMapper.map(cardDTO, Card.class);
        Card card = cardService.save(cardRequest);
        // convert entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable(name = "id") Integer id, @RequestBody CardDTO cardDTO) {
        // convert DTO to entity
        Card cardRequest = modelMapper.map(cardDTO, Card.class);
        Card card = cardService.update(id, cardRequest);
        // convert entity to DTO
        CardDTO cardResponse = modelMapper.map(card, CardDTO.class);
        return ResponseEntity.ok().body(cardResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "id") Integer id) {
        cardService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
 */
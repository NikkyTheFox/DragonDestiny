package com.example.game.card.enemycard.controller;

import com.example.game.card.card.dto.CardDTO;
import com.example.game.card.card.entity.Card;
import com.example.game.card.card.service.CardService;
import com.example.game.card.enemycard.dto.EnemyCardDTO;
import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.enemycard.repository.EnemyCardRepository;
import com.example.game.card.enemycard.service.EnemyCardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/enemycards")
public class EnemyCardController {
    private ModelMapper modelMapper;
    private EnemyCardService enemyCardService;
    @Autowired
    EnemyCardController(EnemyCardService enemyCardService, ModelMapper modelMapper) {
        this.enemyCardService = enemyCardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<EnemyCardDTO> getAllEnemyCards() {
        return enemyCardService.findAll().stream()
                .map(enemyCard -> modelMapper.map(enemyCard, EnemyCardDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EnemyCardDTO> getEnemyCardById(@PathVariable(name = "id") Integer id) {
        EnemyCard enemyCard = enemyCardService.findById(id);
        // convert board entity to DTO
        EnemyCardDTO enemyCardResponse = modelMapper.map(enemyCard, EnemyCardDTO.class);
        return ResponseEntity.ok().body(enemyCardResponse);
    }
    @PostMapping
    public ResponseEntity<EnemyCardDTO> createEnemyCard(@RequestBody EnemyCardDTO enemyCardDTO){
        // convert DTO to entity
        EnemyCard enemyCardRequest = modelMapper.map(enemyCardDTO, EnemyCard.class);
        EnemyCard enemyCard = enemyCardService.save(enemyCardRequest);
        // convert entity to DTO
        EnemyCardDTO enemyCardResponse = modelMapper.map(enemyCard, EnemyCardDTO.class);
        return ResponseEntity.ok().body(enemyCardResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnemyCardDTO> updateEnemyCard(@PathVariable(name = "id") Integer id, @RequestBody EnemyCardDTO enemyCardDTO) {
        // convert DTO to entity
        EnemyCard enemyCardRequest = modelMapper.map(enemyCardDTO, EnemyCard.class);
        EnemyCard enemyCard = enemyCardService.update(id, enemyCardRequest);
        // convert entity to DTO
        EnemyCardDTO enemyCardResponse = modelMapper.map(enemyCard, EnemyCardDTO.class);
        return ResponseEntity.ok().body(enemyCardResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnemyCard(@PathVariable(name = "id") Integer id) {
        enemyCardService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}

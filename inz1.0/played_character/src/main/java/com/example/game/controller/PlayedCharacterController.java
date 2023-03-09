package com.example.game.controller;

import com.example.game.dto.GetPlayedCharacterResponse;
import com.example.game.entities.character.PlayedCharacter;
import com.example.game.entities.character.PlayedCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class PlayedCharacterController {

    private PlayedCharacterService characterService;

    @Autowired
    public PlayedCharacterController(PlayedCharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("{characterId}")
    public ResponseEntity<GetPlayedCharacterResponse> getCharacter(@PathVariable("characterId") Integer characterId) {
        Optional<PlayedCharacter> character = characterService.findById(characterId);
        return character.map(value->ResponseEntity.ok(GetPlayedCharacterResponse.entityToDtoMapper().apply(value)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}

package com.example.game.controller;

import com.example.game.dto.CreatePlayedCharacterRequest;
import com.example.game.dto.GetPlayedCharacterResponse;
import com.example.game.dto.GetPlayedCharactersResponse;
import com.example.game.dto.UpdatePlayedCharacterRequest;
import com.example.game.entities.character.PlayedCharacter;
import com.example.game.entities.character.PlayedCharacterRepository;
import com.example.game.entities.character.PlayedCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/playedCharacters")
public class PlayedCharacterController {

    private PlayedCharacterService playedCharacterService;

    @Autowired
    public PlayedCharacterController(PlayedCharacterService playedCharacterService) {
        this.playedCharacterService = playedCharacterService;
    }

    @GetMapping
    public ResponseEntity<GetPlayedCharactersResponse> GetPlayedCharacters(){
        return ResponseEntity.ok(GetPlayedCharactersResponse.entityToDtoMapper().apply((Collection<PlayedCharacter>) playedCharacterService.findAll()));
    }

    @GetMapping("{characterId}")
    public ResponseEntity<GetPlayedCharacterResponse> getCharacter(@PathVariable("characterId") Integer characterId) {
        Optional<PlayedCharacter> character = playedCharacterService.findById(characterId);
        return character.map(value->ResponseEntity.ok(GetPlayedCharacterResponse.entityToDtoMapper().apply(value)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createPlayedCharacter(@RequestBody CreatePlayedCharacterRequest request, UriComponentsBuilder builder){
        PlayedCharacter playedCharacter = CreatePlayedCharacterRequest.dtoToEntityMapper().apply(request);
        playedCharacterService.save(playedCharacter);
        return ResponseEntity.created(
                builder.pathSegment("api","playedCharacters","{id}")
                        .buildAndExpand(playedCharacter.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlayedCharacter(@PathVariable("id") Integer id){
        Optional<PlayedCharacter> playedCharacter = playedCharacterService.findById(id);
        if(playedCharacter.isPresent()){
            playedCharacterService.deleteById(playedCharacter.get().getId());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePlayedCharacter(@RequestBody UpdatePlayedCharacterRequest request, @PathVariable("id") Integer id){
        Optional<PlayedCharacter> playedCharacter = playedCharacterService.findById(id);
        if(playedCharacter.isPresent()){
            UpdatePlayedCharacterRequest.dtoToEntityMapper().apply(playedCharacter.get(),request);
            playedCharacterService.update(playedCharacter.get());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

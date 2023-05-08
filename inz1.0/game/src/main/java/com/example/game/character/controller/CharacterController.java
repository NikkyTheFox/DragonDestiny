package com.example.game.character.controller;

import com.example.game.character.dto.CharacterDTO;
import com.example.game.character.entity.Character;
import com.example.game.character.service.CharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api/characters"})
public class CharacterController {

    private ModelMapper modelMapper;
    private CharacterService characterService;
    @Autowired
    CharacterController(CharacterService characterService, ModelMapper modelMapper) {
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<CharacterDTO> getAllCharacters() {
        return characterService.findAll().stream()
                .map(character -> modelMapper.map(character, CharacterDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable(name = "id") Integer id) {
        Character character = characterService.findById(id);
        // convert board entity to DTO
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> createCharacter(@RequestBody CharacterDTO characterDTO){
        // convert DTO to entity
        Character characterRequest = modelMapper.map(characterDTO, Character.class);
        Character character = characterService.save(characterRequest);
        // convert entity to DTO
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> updateCharacter(@PathVariable(name = "id") Integer id, @RequestBody CharacterDTO characterDTO) {
        // convert DTO to entity
        Character characterRequest = modelMapper.map(characterDTO, Character.class);
        Character character = characterService.update(id, characterRequest);
        // convert entity to DTO
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable(name = "id") Integer id) {
        characterService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}

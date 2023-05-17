package com.example.game_engine.character.controller;

import com.example.game_engine.character.dto.CharacterDTO;
import com.example.game_engine.character.entity.Character;
import com.example.game_engine.character.service.CharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get characters.
 * Requests go through /api/characters - they represent all characters, not only those assigned to games.
 */

@RestController
@RequestMapping(value = {"/api/characters"})
public class CharacterController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * CharacterService used to communicate with service layer that will communicate with database repository.
     */
    private final CharacterService characterService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param characterService
     * @param modelMapper
     */
    @Autowired
    CharacterController(CharacterService characterService, ModelMapper modelMapper) {
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all characters.
     * @return list of CharacterDTOs
     */
    @GetMapping()
    public List<CharacterDTO> getAllCharacters() {
        return characterService.findAll().stream()
                .map(character -> modelMapper.map(character, CharacterDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve character by ID.
     * @param id - identifier of character
     * @return ResponseEntity containing CharacterDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable(name = "id") Integer id) {
        Character character = characterService.findById(id);
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

}
